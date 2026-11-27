// Primera etapa de migración React -> HTML/JS.
// Carga los productos reales de Spring Boot y reproduce las tarjetas de Dashboard.jsx.
// No ejecuta todavía escrituras sobre MySQL: falta conectar POST/PUT/DELETE y JWT.
const API_PRODUCTOS = 'http://localhost:8081/productos';
const POR_PAGINA = 8;
let productos = [];
let paginaActual = 1;
let productoEnEdicion = null;

const $ = (selector) => document.querySelector(selector);
const precioARS = new Intl.NumberFormat('es-AR', { style: 'currency', currency: 'ARS', maximumFractionDigits: 2 });
const texto = (valor) => valor == null ? '' : String(valor);
const categoriaNombre = (p) => typeof p.categoria === 'string' ? p.categoria : p.categoria?.nombre ?? 'Sin categoría';
const imagen = (p) => p.rutaImagen ?? p.ruta_imagen ?? '';
const API_LOGIN = 'http://localhost:8081/auth/login';

let tokenAdmin = sessionStorage.getItem('optic_admin_token') || '';


function mensaje(m) { $('#status').textContent = m; }
function mensajeFormulario(m) { $('#form-status').textContent = m; }

function renderProductos() {
    const lista = $('#product-list');
    lista.replaceChildren();
    $('#product-counter').textContent = `${productos.length} productos`;
    const paginas = Math.max(1, Math.ceil(productos.length / POR_PAGINA));
    paginaActual = Math.min(paginaActual, paginas);
    const inicio = (paginaActual - 1) * POR_PAGINA;
    productos.slice(inicio, inicio + POR_PAGINA).forEach((p) => {
        const li = document.createElement('li'); li.className = 'product-card';
        const img = document.createElement('img'); img.className = 'product-image';
        img.src = imagen(p) || './pictures/imagen-no-disponible.jpg'; img.alt = p.nombre || 'Producto';
        img.addEventListener('error', () => { img.style.visibility = 'hidden'; });
        const info = document.createElement('div'); info.className = 'product-info';
        const nombre = document.createElement('span'); nombre.className = 'product-name'; nombre.textContent = p.nombre ?? '';
        const precio = document.createElement('span'); precio.className = 'product-price'; precio.textContent = precioARS.format(Number(p.precio) || 0);
        const categoria = document.createElement('span'); categoria.className = 'product-category'; categoria.textContent = categoriaNombre(p);
        info.append(nombre, precio, categoria);
        const actions = document.createElement('div'); actions.className = 'product-actions';
        const editar = document.createElement('button'); editar.className = 'btn-edit'; editar.type = 'button'; editar.textContent = 'Editar';
        editar.addEventListener('click', () => seleccionarProducto(p));
        const eliminar = document.createElement('button'); eliminar.className = 'btn-delete'; eliminar.type = 'button'; eliminar.textContent = 'Eliminar';
        eliminar.addEventListener('click', () => eliminarProducto(p));
        actions.append(editar, eliminar); li.append(img, info, actions); lista.append(li);
    });
    if (!productos.length) mensaje('No se encontraron productos.');
    const navegacion = $('#page-numbers'); navegacion.replaceChildren();
    const comienzo = Math.max(1, Math.min(paginaActual - 2, paginas - 4));
    for (let num = comienzo; num <= Math.min(paginas, comienzo + 4); num++) {
        const btn = document.createElement('button'); btn.type = 'button'; btn.className = 'page-button' + (num === paginaActual ? ' current' : '');
        btn.textContent = String(num); btn.setAttribute('aria-label', `Página ${num}`);
        if (num === paginaActual) btn.setAttribute('aria-current', 'page');
        btn.addEventListener('click', () => { paginaActual = num; renderProductos(); }); navegacion.append(btn);
    }
    $('#prev-page').disabled = paginaActual === 1;
    $('#next-page').disabled = paginaActual === paginas;
}

function cargarCategorias() {
    const select = $('#product-category');
    const actual = select.value;

    select.replaceChildren(
        new Option('Seleccionar una categoría', '')
    );

    const categorias = new Map();

    productos.forEach(p => {
        if (p.categoria?.id != null) {
            categorias.set(p.categoria.id, p.categoria.nombre);
        }
    });

    [...categorias.entries()]
        .sort((a, b) => a[1].localeCompare(b[1], 'es'))
        .forEach(([id, nombre]) => {
            select.add(new Option(nombre, String(id)));
        });

    select.value = actual;
}

function seleccionarProducto(p) {
    productoEnEdicion = p;
    $('#form-heading').textContent = 'Editar producto';
    $('#save-product').textContent = 'Actualizar producto';
    $('#cancel-edit').hidden = false;
    $('#product-name').value = texto(p.nombre);
    $('#product-description').value = texto(p.descripcion);
    $('#product-price').value = texto(p.precio);
    $('#product-stock').value = texto(p.stock);
    $('#product-category').value = String(p.categoria?.id ?? '');
    $('#product-image').value = imagen(p); actualizarImagen();
    $('.form-section').scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

async function eliminarProducto(p) {
    const confirmar = confirm(
        `¿Estás segura de eliminar el producto ${p.nombre}?`
    );

    if (!confirmar) return;

    if (!tokenAdmin) {
        mensajeFormulario('Primero iniciá sesión como administrador.');
        return;
    }

    try {
        const respuesta = await fetch(`${API_PRODUCTOS}/${p.id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${tokenAdmin}`
            }
        });

        if (!respuesta.ok) {
            throw new Error(`HTTP ${respuesta.status}`);
        }

        if (productoEnEdicion?.id === p.id) {
            limpiarFormulario();
        }

        await cargarProductos();
        mensajeFormulario('Producto eliminado correctamente');

    } catch (error) {
        console.error(error);
        mensajeFormulario('No se pudo eliminar: ' + error.message);
    }
}

function limpiarFormulario() {
    productoEnEdicion = null; $('#product-form').reset();
    $('#form-heading').textContent = 'Agregar nuevo producto';
    $('#save-product').textContent = 'Guardar producto';
    $('#cancel-edit').hidden = true;
    actualizarImagen(); mensajeFormulario('');
}
function actualizarImagen() {
    const ruta = $('#product-image').value.trim();
    $('#image-preview').hidden = !ruta;
    if (ruta) $('#preview-img').src = ruta;
}

async function cargarProductos() {
    try {
        const r = await fetch(API_PRODUCTOS);
        if (!r.ok) throw new Error(`HTTP ${r.status}`);
        const datos = await r.json();
        if (!Array.isArray(datos)) throw new Error('La API no devolvió una lista de productos');
        productos = datos; paginaActual = 1;
        mensaje(''); cargarCategorias(); renderProductos();
    } catch (err) {
        mensaje(`No pudimos cargar el catálogo desde ${API_PRODUCTOS}: ${err.message}. Comprobá que Spring Boot esté iniciado.`);
        console.error(err);
    }
}

$('#prev-page').addEventListener('click', () => { if (paginaActual > 1) { paginaActual--; renderProductos(); } });
$('#next-page').addEventListener('click', () => { if (paginaActual < Math.ceil(productos.length / POR_PAGINA)) { paginaActual++; renderProductos(); } });
$('#cancel-edit').addEventListener('click', limpiarFormulario);
$('#product-image').addEventListener('input', actualizarImagen);
$('#product-form').addEventListener('submit', async (evento) => {
    evento.preventDefault();

    if (!tokenAdmin) {
        mensajeFormulario('Primero iniciá sesión como administrador.');
        return;
    }

    if (!evento.currentTarget.reportValidity()) return;


    const datosActualizados = {
        nombre: $('#product-name').value.trim(),
        descripcion: $('#product-description').value.trim(),
        precio: Number($('#product-price').value),
        stock: Number($('#product-stock').value),
        categoriaId: Number($('#product-category').value),
        rutaImagen: $('#product-image').value.trim()
    };

    if (!$('#product-category').value) {
        mensajeFormulario('Seleccioná una categoría.');
        return;
    }

    const esEdicion = productoEnEdicion !== null;

    const metodo = esEdicion ? 'PUT' : 'POST';

    const url = esEdicion
        ? `${API_PRODUCTOS}/${productoEnEdicion.id}`
        : API_PRODUCTOS;

    try {
        const respuesta = await fetch(url, {
            method: metodo,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${tokenAdmin}`
            },
            body: JSON.stringify(datosActualizados)
        });

        if (!respuesta.ok) {
            throw new Error(`HTTP ${respuesta.status}`);
        }

        limpiarFormulario();
        await cargarProductos();

        mensajeFormulario(
            esEdicion
                ? 'Producto actualizado correctamente'
                : 'Producto agregado correctamente');

    } catch (error) {
        console.error(error);
        mensajeFormulario(
            `No se pudo ${esEdicion ? 'actualizar' : 'agregar'} el producto: ${error.message}`
        );
    }
});
function actualizarVistaSesion() {
    const autenticado = Boolean(tokenAdmin);

    $('#login-panel').hidden = autenticado;
    $('#logout-admin').hidden = !autenticado;
}

$('#login-form').addEventListener('submit', async (evento) => {
    evento.preventDefault();

    const usuario = $('#login-user').value.trim();
    const clave = $('#login-password').value;
    console.log('Usuario:', JSON.stringify(usuario));
    console.log('Caracteres de la contraseña:', clave.length);
    try {
        const respuesta = await fetch(API_LOGIN, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                // 'Authorization': `Bearer ${tokenAdmin}`
            },
            body: JSON.stringify({ usuario, clave })
        });

        if (!respuesta.ok) {
            throw new Error(`Error HTTP ${respuesta.status}`);
        }

        const datos = await respuesta.json();

        tokenAdmin = datos.token;
        sessionStorage.setItem('optic_admin_token', tokenAdmin);

        $('#login-password').value = '';
        $('#login-status').textContent =
            'Sesión iniciada correctamente';

        actualizarVistaSesion();

    } catch (error) {
        $('#login-status').textContent = error.message;
    }
});

$('#logout-admin').addEventListener('click', () => {
    tokenAdmin = '';
    sessionStorage.removeItem('optic_admin_token');

    $('#login-status').textContent = 'Sesión cerrada';
    actualizarVistaSesion();
});

actualizarVistaSesion();
cargarProductos();