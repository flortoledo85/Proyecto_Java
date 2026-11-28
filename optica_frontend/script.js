// FUNCIONES DE MI PROGRAMA

let listaCarrito = [];

let productos = [];
//JSON
/**
 * Funcion asincronica para traer los objetos de JSON
 * @returns {Promise<Array<Object>>}
 */

async function cargarProductosApi() {
    try {
        const respuesta = await fetch("http://localhost:8081/productos");
        if (!respuesta.ok) {
            throw new Error(`Error al obtener los datos ${respuesta.status} - ${respuesta.statusText}`);
        }
        const datos = await respuesta.json();
        console.log("Datos recibidos por spring: ", datos);

        const productosArray = datos.map(p => ({
            id: p.id,
            name: p.nombre,
            price: Number(p.precio),
            pathimg: p.rutaImagen,
            description: p.descripcion ?? "Sin descripcion",
            // description: p.medidas
            //     ? `Medidas:
            //     Ancho de frente: ${p.medidas.anchoFrente} mm.
            //     Largo de patilla: ${p.medidas.largoPatilla} mm.
            //     Ancho de cristal: ${p.medidas.anchoCristal} mm.
            //     Alto de cristal: ${p.medidas.altoCristal} mm.`
            //     : "Medidas no disponibles",
            stock: p.stock,
            category: typeof p.categoria === "string"
                ? p.categoria
                : p.categoria?.nombre ?? "Sin categoría",
        }));
        // const productosArray = await respuesta.json();
        return productosArray;
    }
    catch (datosError) {
        console.error("Fallo en la carga de datos: ", datosError);
        const contenedor = document.querySelector("#productos .catalogo");
        contenedor.innerHTML = `<p id="mensaje-error"> Error al cargar el catalogo.</p>`
    }
}

function crearProducto(idProd, nameProd, descriptionProd, pathProducto, amountProd) {
    const nuevoProducto = {
        id: idProd,
        name: nameProd,
        description: descriptionProd,
        pathimg: pathProducto,
        price: amountProd
    };
    return nuevoProducto;
}

function mostrarCatalogo1(productos) {
    for (let j = 0; j < productos.length; j++) {
        console.log(`ID: ${productos[j].id} | Producto: ${productos[j].name} | Precio: ${productos[j].price}`);
    };
}

function copiaListaProductos(lista) {
    return [...lista];
}

function mostrarCatalogo2(catalogo) {
    for (const { id, name, price } of catalogo) {
        console.log(` ID: ${id} | Nombre: ${name} | Precio: ${price.toFixed(2)}`);
    };
}

function calcularTotal(productos) {
    return productos.reduce(
        (total, p) => total + p.price * (p.cantidad ?? 1), 0);
}

function buscarEnLista(catalogo, idBuscado) {
    for (const { id } of catalogo) {
        if (id === idBuscado) {
            return id
        }
    }
    return -1;
}

function buscarProductoID(catalogo, idBuscado) {
    for (const item of catalogo) {
        if (item.id === idBuscado) {
            const { description, ...resto } = item;
            return resto;
        }
    }
}


function agregarProducto(catalogo, nuevoProducto) {
    const catalogoActualizado = [...catalogo, nuevoProducto];
    return catalogoActualizado
}

function insertarProducto(productos) {
    // const listaProductos = copiaListaProductos(listaNueva);
    const contenedorProductos = document.querySelector("#productos .catalogo");
    // console.log(contenedorProductos);

    for (let c = 0; c < productos.length; c++) {
        // console.log(c)
        const productoActual = productos[c];
        const nuevoElemento = document.createElement("article");
        nuevoElemento.className = "producto";
        const precio = productoActual.price
        nuevoElemento.innerHTML = `
            <a href="${productoActual.pathimg}" target="_blank">
            <img src="${productoActual.pathimg}" alt="${productoActual.description}"></a>
            <h3>${productoActual.name}</h3>
            <p class="categoria_producto">${productoActual.category}</p>
            <p class= "stock">
                Stock disponible: ${productoActual.stock ?? "No disponible"}
            </p>
            <p>
                <a class="description_product" href="#." data-descripcion="${productoActual.description}"> 
                Descripción &#xFF0B;</a>
            </p>
            <div class="contenedor_descripcion">
            </div> 
            <p class= "precio">
                Precio = ${precio.toLocaleString("es-AR", { style: "currency", currency: "ARS" })}<br>
            </p>
            <button
                class="btn-primary-custom"
                type="button"
                data-id="${productoActual.id}"
                data-nombre="${productoActual.name}"
                data-precio="${precio}">
                Agregar al Carrito
            </button>`
        contenedorProductos.appendChild(nuevoElemento);
    }
}

function insertarProductoHTML(producto) {
    const listaCarritoHTML = document.querySelector("#carrito .list-group");
    const liProducto = document.createElement("li");
    liProducto.textContent = `${producto.name} ${producto.price.toLocaleString("es-AR", { style: "currency", currency: "ARS" })}`;
    liProducto.className = "list-group-item";
    listaCarritoHTML.appendChild(liProducto);
}

function filtrarProductos() {

    const texto = document.querySelector("#buscarProducto")
        .value.trim().toLowerCase();

    const categoria = document.querySelector("#filtrarCategoria").value;

    const encontrados = productos.filter(p => {
        const coincideNombre = p.name.toLowerCase().includes(texto);

        const coincideCategoria =
            categoria === "" || p.category === categoria;

        return coincideNombre && coincideCategoria;
    });

    const catalogo = document.querySelector("#productos .catalogo");
    catalogo.innerHTML = "";

    if (encontrados.length === 0) {
        catalogo.innerHTML =
            '<p style="color:white;">No se encontraron productos.</p>';
    } else {
        insertarProducto(encontrados);
    }

    actualizarStockVisible();
}

function cargarCategorias() {
    const select = document.querySelector("#filtrarCategoria");

    const categorias = [...new Set(
        productos.map(p => p.category)
    )].sort();

    categorias.forEach(categoria => {
        const opcion = document.createElement("option");
        opcion.value = categoria;
        opcion.textContent = categoria;
        select.appendChild(opcion);
    });
}

function actualizaContador() {
    const contenedorNumero = document.querySelector("#carrito .contador_compra");
    contenedorNumero.textContent = listaCarrito.length;
}

function guardarCarritoEnStorage(listaCarrito) {
    const carritoJSON = JSON.stringify(listaCarrito);
    localStorage.setItem("listaCarrito", carritoJSON);
}

function vaciarCarritoHTML() {
    listaCarrito = [];
    guardarCarritoEnStorage(listaCarrito);
    renderizarCarrito();
}

function renderizarCarrito() {
    const lista = document.querySelector("#carrito .list-group");
    lista.innerHTML = "";

    listaCarrito.forEach(p => {
        const producto = productos.find(item => item.id === p.id);
        const subtotal = p.price * p.cantidad;

        const li = document.createElement("li");
        li.className = "list-group-item d-flex justify-content-between align-items-center gap-2";

        const detalle = document.createElement("span");
        detalle.textContent =
            `${p.name} — ${subtotal.toLocaleString("es-AR", {
                style: "currency",
                currency: "ARS"
            })}`;

        const controles = document.createElement("div");

        controles.innerHTML = `
            <button type="button" class="btn btn-sm btn-outline-secondary"
                data-accion="restar" data-id="${p.id}">−</button>

            <span class="mx-2">${p.cantidad}</span>

            <button type="button" class="btn btn-sm btn-outline-secondary"
                data-accion="sumar" data-id="${p.id}"
                ${!producto || p.cantidad >= producto.stock ? "disabled" : ""}>+</button>
        `;

        li.append(detalle, controles);
        lista.appendChild(li);
    });

    const unidades = listaCarrito.reduce(
        (total, p) => total + p.cantidad, 0
    );

    document.querySelector("#carrito .contador_compra").textContent = unidades;

    document.querySelector("#carrito .montoTotal").textContent =
        calcularTotal(listaCarrito).toLocaleString("es-AR", {
            style: "currency",
            currency: "ARS"
        });

    actualizarStockVisible();
}

function cambiarCantidadCarrito(evento) {

    const boton = evento.target.closest("button[data-accion]");
    if (!boton) return;

    const id = Number(boton.dataset.id);
    const accion = boton.dataset.accion;

    const item = listaCarrito.find(p => p.id === id);
    const producto = productos.find(p => p.id === id);

    if (!item || !producto) return;

    if (accion === "sumar") {
        if (item.cantidad >= producto.stock) return;

        item.cantidad++;
    }

    if (accion === "restar") {
        item.cantidad--;

        if (item.cantidad === 0) {
            listaCarrito = listaCarrito.filter(p => p.id !== id);
        }
    }

    guardarCarritoEnStorage(listaCarrito);
    renderizarCarrito();
}

function mostrarDescripcion(evento) {
    const elementoEvento = evento.target.tagName;
    if (elementoEvento === "A") {
        const elementoClickeado = evento.target;
        const descripcionProducto = elementoClickeado.dataset.descripcion;
        // console.log(descripcionProducto)
        const divProducto = elementoClickeado.closest(".producto");
        const divDescrip = divProducto.querySelector(".contenedor_descripcion");
        // console.log(divDescrip);
        if (divDescrip.children.length == 0) {
            const elementoDescrip = document.createElement("p");
            elementoDescrip.textContent = descripcionProducto;
            divDescrip.appendChild(elementoDescrip);
            elementoClickeado.textContent = "Descripción \u2212";
        }
        else {
            elementoClickeado.textContent = "Descripción \uFF0B";
            divDescrip.innerHTML = "";
        }
    }
}

function agregarAlCarrito(evento) {

    if (evento.target.tagName !== "BUTTON") return;

    const idProducto = Number(evento.target.dataset.id);
    const producto = productos.find(p => p.id === idProducto);
    if (!producto) return;

    const existente = listaCarrito.find(p => p.id === idProducto);
    const cantidadActual = existente?.cantidad ?? 0;

    if (cantidadActual >= producto.stock) {
        alert("No hay stock suficiente");
        return;
    }

    if (existente) {
        existente.cantidad = cantidadActual + 1;
    } else {
        listaCarrito.push({
            ...producto, cantidad: 1
        });
    }
    guardarCarritoEnStorage(listaCarrito);
    renderizarCarrito();
}

function cargarCarritoStorage() {
    const carritoJSON = localStorage.getItem("listaCarrito");
    // console.log(carritoJSON);
    if (carritoJSON) {
        return JSON.parse(carritoJSON);
    }
    else {
        return [];
    }
}

function eliminarCarritoEnStorage() {
    localStorage.removeItem("listaCarrito");
    listaCarrito = [];
    actualizaContador();
    vaciarCarritoHTML();
}

function calcularMontoTotal(evento) {
    const carrito = cargarCarritoStorage();
    if (carrito) {
        const total = calcularTotal(carrito);
        const elementoTotal = evento.target;
        // console.log(elementoTotal);
        const divMonto = elementoTotal.closest(".total");
        // console.log(divMonto);
        const divTotal = divMonto.querySelector(".montoTotal");
        divTotal.textContent = total.toLocaleString("es-AR", { style: "currency", currency: "ARS" });
    }
}

function actualizarStockVisible() {

    productos.forEach(producto => {

        const enCarrito = listaCarrito.find(
            p => p.id === producto.id
        );

        const cantidad = enCarrito?.cantidad ?? 0;
        const disponible = Math.max(0, producto.stock - cantidad);

        const boton = document.querySelector(
            `#productos .producto button[data-id="${producto.id}"]`
        );

        if (!boton) return;

        const tarjeta = boton.closest(".producto");
        const stockHTML = tarjeta.querySelector(".stock");

        if (stockHTML) {
            stockHTML.textContent = `Stock disponible: ${disponible}`;
        }

        boton.disabled = disponible === 0;
        boton.textContent = disponible === 0
            ? "Sin stock disponible"
            : "Agregar al Carrito";
    });
}

async function main() {
    productos = await cargarProductosApi();

    if (productos && productos.length > 0) {
        insertarProducto(productos);
    }
    else {
        alert("No se pueden cargar los productos. Intente mas tarde")
    }

    cargarCategorias();

    const buscador = document.querySelector("#buscarProducto");
    buscador.addEventListener("input", filtrarProductos);
    document.querySelector("#filtrarCategoria").addEventListener("change", filtrarProductos);


    const contenedorProducto = document.querySelector("#productos .catalogo");
    // console.log(contenedorProducto);
    contenedorProducto.addEventListener("click", mostrarDescripcion);
    contenedorProducto.addEventListener("click", agregarAlCarrito);

    const vaciarCarrito = document.querySelector("#carrito .vaciarCarrito");
    vaciarCarrito.addEventListener("click", eliminarCarritoEnStorage);
    document.querySelector("#confirmarPedido").addEventListener("click", confirmarPedido);
    document.querySelector("#usuarioPedido").value = sessionStorage.getItem("cliente_usuario") || "";
    document.querySelector("#ver-mis-pedidos").addEventListener("click", cargarMisPedidos);
    const listaCarritoHTML = document.querySelector("#carrito .list-group");
    listaCarritoHTML.addEventListener("click", cambiarCantidadCarrito);

    listaCarrito = cargarCarritoStorage().map(p => ({
        ...p,
        cantidad: p.cantidad ?? 1
    }));

    
    renderizarCarrito();
}

function validarFormulario(evento) {
    evento.preventDefault();
    const nombre = document.querySelector("#nombre").value;
    const email = document.querySelector("#email").value;
    if (nombre === "" || email === "") {
        alert("Los campos son obligatorios");
        return;
    }
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailRegex.test(email)) {
        alert("Por favor ingrese un mail valido");
        return;
    }

    // console.log("Formulario correcto");
    evento.target.submit();
    evento.target.reset();

}

const API_AUTH = 'http://localhost:8081/auth';

document.querySelector('#registro-form')
    .addEventListener('submit', async (evento) => {

        evento.preventDefault();

        const nombreUsuario =
            document.querySelector('#registro-usuario').value.trim();

        const email =
            document.querySelector('#registro-email').value.trim();

        const clave =
            document.querySelector('#registro-clave').value;

        try {
            const respuesta = await fetch(`${API_AUTH}/registro`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    nombreUsuario,
                    email,
                    clave
                })
            });

            if (!respuesta.ok) {
                throw new Error(`HTTP ${respuesta.status}`);
            }

            const usuario = await respuesta.json();

            document.querySelector('#auth-cliente-status').textContent =
                `Usuario ${usuario.nombreUsuario} registrado correctamente.`;

            document.querySelector('#cliente-usuario').value =
                usuario.nombreUsuario;

            document.querySelector('#registro-form').reset();

        } catch (error) {
            document.querySelector('#auth-cliente-status').textContent =
                `No se pudo registrar: ${error.message}`;
        }
    });


document.querySelector('#login-cliente-form')
    .addEventListener('submit', async (evento) => {

        evento.preventDefault();

        const usuario =
            document.querySelector('#cliente-usuario').value.trim();

        const clave =
            document.querySelector('#cliente-clave').value;

        try {
            const respuesta = await fetch(`${API_AUTH}/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ usuario, clave })
            });

            if (!respuesta.ok) {
                throw new Error(`HTTP ${respuesta.status}`);
            }

            const datos = await respuesta.json();

            sessionStorage.setItem('cliente_token', datos.token);
            sessionStorage.setItem('cliente_usuario', usuario);

            document.querySelector('#auth-cliente-status').textContent =
                'Sesión iniciada correctamente';

            mostrarSesionCliente(usuario);
            cargarMisPedidos();

        } catch (error) {
            document.querySelector('#auth-cliente-status').textContent =
                `No se pudo iniciar sesión: ${error.message}`;
        }
    });

document.querySelector('#cliente-logout')
    .addEventListener('click', () => {

        sessionStorage.removeItem('cliente_token');
        sessionStorage.removeItem('cliente_usuario');

        document.querySelector('#cliente-sesion').hidden = true;
        document.querySelector('#registro-form').hidden = false;
        document.querySelector('#login-cliente-form').hidden = false;
        document.querySelector("#lista-mis-pedidos").replaceChildren();
        document.querySelector("#pedidos-estado").textContent = "Iniciá sesión para consultar tus pedidos.";
        document.querySelector('#auth-cliente-status').textContent =
            'Sesión cerrada';
    });

function mostrarSesionCliente(usuario) {
    document.querySelector("#cliente-nombre").textContent =
        `Bienvenido, ${usuario}`;
    document.querySelector("#usuarioPedido").value = usuario;
    document.querySelector("#cliente-sesion").hidden = false;
    document.querySelector("#registro-form").hidden = true;
    document.querySelector("#login-cliente-form").hidden = true;

}

const tokenGuardado = sessionStorage.getItem("cliente_token");
const usuarioGuardado = sessionStorage.getItem("cliente_usuario");

if (tokenGuardado && usuarioGuardado) {
    mostrarSesionCliente(usuarioGuardado);
    cargarMisPedidos();
}
// EJECUCION DE MI PROGRAMA

async function confirmarPedido() {

    const estado = document.querySelector("#pedido-estado");
    const boton = document.querySelector("#confirmarPedido");
    const emailInput = document.querySelector("#emailPedido");

    const token = sessionStorage.getItem("cliente_token");
    const usuario = sessionStorage.getItem("cliente_usuario");

    if (!token || !usuario) {
        estado.textContent = "Primero tenés que iniciar sesión.";
        return;
    }

    if (listaCarrito.length === 0) {
        estado.textContent = "El carrito está vacío.";
        return;
    }

    if (!emailInput.reportValidity()) return;

    const datosPedido = {
        usuario: usuario,
        email: emailInput.value.trim(),
        lineas: listaCarrito.map(p => ({
            productoId: p.id,
            cantidad: p.cantidad
        }))
    };

    boton.disabled = true;
    estado.textContent = "Procesando pedido...";

    try {
        const respuesta = await fetch(
            "http://localhost:8081/pedidos",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(datosPedido)
            }
        );

        if (!respuesta.ok) {
            if (respuesta.status === 401) {
                throw new Error("La sesión venció. Volvé a iniciar sesión.");
            }
            throw new Error(`HTTP ${respuesta.status}`);
        }

        const pedido = await respuesta.json();

        listaCarrito = [];
        guardarCarritoEnStorage(listaCarrito);
        renderizarCarrito();

        estado.textContent =
            `¡Pedido N.º ${pedido.id} confirmado correctamente!`;

        // Recargamos los productos para reflejar el nuevo stock.
        const nuevosProductos = await cargarProductosApi();

        if (Array.isArray(nuevosProductos)) {
            productos = nuevosProductos;
            filtrarProductos();
        }

    } catch (error) {
        console.error(error);
        estado.textContent =
            `No se pudo confirmar el pedido: ${error.message}`;
    } finally {
        boton.disabled = false;
    }
}

async function cargarMisPedidos() {

    const lista = document.querySelector("#lista-mis-pedidos");
    const estado = document.querySelector("#pedidos-estado");

    const token = sessionStorage.getItem("cliente_token");

    lista.replaceChildren();

    if (!token) {
        estado.textContent =
            "Iniciá sesión para consultar tus pedidos.";
        return;
    }

    estado.textContent = "Cargando pedidos...";

    try {
        const respuesta = await fetch(
            "http://localhost:8081/pedidos/mis",
            {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
        );

        if (respuesta.status === 401) {
            throw new Error(
                "La sesión venció. Volvé a iniciar sesión."
            );
        }

        if (!respuesta.ok) {
            throw new Error(`HTTP ${respuesta.status}`);
        }

        const pedidos = await respuesta.json();

        if (pedidos.length === 0) {
            estado.textContent =
                "Todavía no realizaste ninguna compra.";
            return;
        }

        estado.textContent =
            `Tenés ${pedidos.length} pedido(s) registrado(s).`;

        pedidos.forEach(pedido => {

            const elemento = document.createElement("li");
            elemento.className = "list-group-item";

            const titulo = document.createElement("h3");
            titulo.textContent = `Pedido N.º ${pedido.id}`;

            const fecha = document.createElement("p");
            fecha.textContent =
                `Fecha: ${pedido.fecha.replace("T", " ").slice(0, 16)}`;

            const productosPedido = document.createElement("ul");

            pedido.lineas.forEach(linea => {

                const item = document.createElement("li");

                const subtotal = Number(linea.subtotal)
                    .toLocaleString("es-AR", {
                        style: "currency",
                        currency: "ARS"
                    });

                item.textContent =
                    `${linea.producto} — ` +
                    `${linea.cantidad} unidad(es) — ${subtotal}`;

                productosPedido.appendChild(item);
            });

            const total = document.createElement("p");

            total.textContent =
                `Total: ${Number(pedido.total).toLocaleString("es-AR", {
                    style: "currency",
                    currency: "ARS"
                })}`;

            elemento.append(titulo, fecha, productosPedido, total);
            lista.appendChild(elemento);
        });

    } catch (error) {
        console.error("Error al consultar pedidos:", error);

        estado.textContent =
            `No se pudieron cargar los pedidos: ${error.message}`;
    }
}


main();
const validacionForm = document.querySelector("#contact .formulario");
// console.log(validacionForm)
validacionForm.addEventListener("submit", validarFormulario);