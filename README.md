# AppHeladeriaReparto
Diseñado con Java, Spring-Boot, Bootstrap y Fontawesome.

**_Desarrollo y Hardware:_**

Este sistema de ventas es un proyecto Maven y fue desarrollado con la plataforma Java 11 (Amazon Corretto 11) en todo el back-end
junto con Spring-Boot 2.5.4 (Versión mas estable), BootStrap y FontAwesome en conjunto para el Front y una base de datos SQL (MySql)
administrada en MySqlWorkbench.
Se utilizó AmazonCorretto 11 ya que su licencia es gratuita y poder compatibilizar con AWS que es donde se desplegó la aplicación.

El hardware que se utilizó para el desarrollo fue:
Notebook Banghó Mov Max 1524:

Procesador Intel 2020m 2,4Ghz.
4 Gb de memoria Ram DDR 1600 mhz.
SDD 256 Gb.
gpu Intel HD2500 up to 1536 Mb.
Sistema Operativo Ubuntu (Linux) 21.04.
IDE: NetBeans, VS Code.

----------------------------------------------------------------------------------------------------------------------------------------

Sistema de Ventas realizado para una distribuidora de helados con el fin de solucionar 
los problemas presentados por el cliente. Este sistema cuenta con varias secciones como lo 
es la sección de:

_Ventas.
Clientes.
Productos.
Ordenes.
Cargas.
Reportes._

**Ventas:** en esta sección podremos realizar una venta en donde tendremos un carrito, busqueda de productos por nombre,
selección de clientes, lista de precio, etc.

**Clientes:** en esta sección podremos ver, agregar, editar o eliminar el/los clientes.

**Productos:** en esta sección podremos ver, agregar, editar o eliminar el/los productos. El precio de los productos no se
muestra en dicha vista, ya que dentro hay una seccion para las "Listas de Precio", en donde sí se encuentran todos los productos
en cada lista creada con sus respectivos precios.

**Ordenes:** en esta sección podremos ver o eliminar las ventas realizadas. Al seleccionar una, podremos visualizar el detalle 
de la venta, ya sea cliente, hora y fecha, productos, total, etc., como así también la posibilidad de exportarla en PDF.

**Cargas:** en esta sección podremos ver el stock de cada uno de los productos. Esto fue realizado así para los tipos de ventas que
utiliza el cliente (Venta directa: Control de la carga y descarga de productos; Pre-Venta: Posibilidad de cargar un numero grande
para evitar los mensajes de "Producto agotado").

**Reportes:** en esta sección podremos ver, agregar o eliminar un reporte de productos. Esta sección fue diseñada para resolver el problema
de contabilizar todos los productos que se realizaban en la pre-venta, ya que se agrupan por nombre y cantidad. También cuenta con la posibilidad
de eportar a PDF.

----------------------------------------------------------------------------------------------------------------------------------------

**_EJECUCIÓN DEL PROYECTO DE MANERA LOCAL:_**
1). En primer lugar deberemos instalar MySql (Versión utilizada 8.23) y crear una nueva instancia local (localhost).
2). Creada la instancia, abrimos el sql que se encuentra en "**resources**" y este creará todo lo necesario para su funcionamiento (Esquema y tablas). 
3). Revisamos el **aplicattion.properties** que tenga el datasource con la direccion a localhost y el puerto 3306 (puerto predeterminado para las BD mysql),
como así también el usuario y contraseña de nuestra instancia local.
4). En la carpeta "**target**" abrimos una ventana de la terminal y escribimos lo siguiente: 
_java -jar app-0.0.1.jar_ y presionamos enter.
5). Una vez inicializado abrimos el navegador y accedemos a: http://localhost:8080/ 

**IMPORTANTE**
Debemos tener instalado el jdk 11 para su ejecución.

Usuario para el sistema : admin
Password: admin
