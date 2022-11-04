# Proyecto UD 1. Acceso a Datos
## Iago Oitaven & Ángel Monroy

### Introducción al Proyecto, API y Postman

El proyecto que hemos desenvuelto consiste en una aplicación que obtiene y muestra al usuario noticias en relación a 
la categoría que este haya seleccionado previamente. La API utilizada es la siguiente: [Inshorts News API](https://github.com/cyberboysumanjay/Inshorts-News-API). 
Pertenece a una página web de la cual obtenemos las 24 noticias más recientes filtradas por la ya mencionada categoría, 
por lo que a fin de tratarlas más fácilmente son convertidas en un único objeto que contiene múltiples noticias cuando 
llegan a la aplicación, tras lo cual son mostradas al usuario en formato de tabla con la cual puede interactuar para 
ver la imágen asociada a la noticia, copiar la URL de esta o selecionar todas o un grupo de ellas para guardar en formato 
binario, JSON, XML o texto plano. A su vez las noticias guardadas en XML, JSON o binario pueden volver a ser cargadas a 
la aplicación para ser visualizadas. El programa también permite al usuario mantener su última sesión seleccionando 
una check box, lo cual hace que una vez vuelva a abrirlo restaure las últimas noticias que haya visto.

> ![postman_science](https://user-images.githubusercontent.com/105040748/195174379-b9c6c825-9c95-4da9-9be4-62c8b4e48363.PNG)
> Captura de la respuesta en POSTMAN

### Manual técnico

El proyecto compartimentaliza la vista, la lógica y las funciones que controlen las acciones como acceso guardado o carga 
a archivos, siguiendo este modelo la vista se comunica con la lógica, que a su vez llama a las funciones de control 
en caso de requerirlo. Tras lo cual la vista se actualiza con lo que la lógica le devuelva, creando, así gracias a este 
sistema, una encapsulación del codigo que lo hace fácil de mantener y ampliar. En sí el código se divide en tres paquetes: 
view, donde se almacena la lógica y relación con la interfaz gráfica en forma de dos clases: MainView y Relational; model, 
donde se encuentran las clases Config, News y RawNews (el formato en el que se guardan los conjuntos de noticias) y mgmt, 
donde se encuentran Log: para la creación de archivos log, Request: para las solicitudes al API, y FileUtils: que se encarga 
de la modificación de ficheros.
Para clonar el repositorio:
```
git clone https://github.com/CGAInstitution/proyectoud1-angel-iago
```
Estructura de directorios:
```
apirequests.proj_u1/     # Directorio principal donde están todas las clases
|- Main.java             # Inicio y carga de la configuración guardada
|- mgmt/                 # Directorio para gestinar las clases relacionadas con uso de ficheros y peticiones a la API
|    └─ FileUtils.java   # Gestión de todo lo relacionado con escritura y lectura de ficheros y configuración
|    └─ Log.java         # Escritura de log para errores y peticiones realizadas a la API
|    └─ Request.java     # Realiza la petición a la API y mappeo del resultado a un objeto tipo RawNews 
|- model/                # Directorio con los modelos de datos y configuración
|    └─ Config.java      # Gestiona la configuración inicial y el autoguardado de la ultima sesión 
|    └─ News.java        # Clase para los atributos de cada noticia
|    └─ RawNews.java     # Modelo creado para mappear la respuesta de la API
|- view/                 # Directorio para gestión de la interfaz gráfica
     └─ MainView.java    # Clase que gestiona todos los elementos que contiene la vista
     └─ Relational       # Controlador, prepara los datos para mostrar
```

Para ejecutar el JAR, incluye este en el directorio principal del proyecto. Si no se ejecuta, escribe el siguiente comando:
```shell
java -jar proyectoud1-angel-iago.jar
```


### Manual de Usuario

En sí la utilización de esta aplicación es de lo más sencilla e intuitiva. Lo único que podrá hacer el usuario al iniciar 
el programa será elegir una categoría de noticias, tras lo cual, pulsando el botón de buscar, encontrará en la tabla una
lista de noticias relacionadas, o cargar un fichero con noticias previamente descargadas.

> ![inicio](https://user-images.githubusercontent.com/94072971/195633739-da53dc1d-3ad0-4271-99c8-5699cb09cc53.PNG)
> Inicio del programa

Una vez cargadas el usuario podrá copiar la URL para ver la noticia en la web, abrir la imagen de 
un artículo para visualizarla o guardar todas o el grupo que seleccione de noticias en un fichero binario, XML, JSON o de 
texto plano. Habiendo hecho esto y teniendo un archivo de noticias al usuario se le presenta la opción de, seleccionando el 
boton de cargar noticias, mostrando las noticias guardadas.

> ![busqueda](https://user-images.githubusercontent.com/94072971/195633763-31e5a5cc-796a-4e0f-b94f-b16e8ccab569.PNG)
> Búsqueda por categoría

Otra funcionalidad más es la de restaurar la última sesión dejando seleccionado el check box designado, lo cual hace que 
se recuperen las últimas noticias que se hayan cargado.

### Reparto de tareas

En este proyecto Iago Oitaven se encargo de la organización, estructura, interfaz grafica, lógica y solicitud a la API; 
mientras que Ángel Monroy realizo la parte de control donde se encuentran los métodos de guardado y cargado de noticias 
y fichero de configuración, log para la creacion de ficheros log e implementacion del sistema de configuración. Ambos 
modificaron y vieron el código de otro permitiendoles obtener un conocimiento general de la aplicación que les sirvió 
para ayudarse entre ellos. Puesto en porcentaje Iago Oitaven contribuyó un 55% mientras que Ángel Monroy un 45% 

### Extras

* Control de errores - En caso de error se crea un archivo log con la información del error para que el usuario visualice.
* Uso de la aplicación offline - Habiendo guardado antes las noticas o selecionando mantener la última sesión se pueden 
  visualizar noticias aun sin conexión a internet.
* Almacenamiento de último estado - Seleccionando la check box para mantener la sesión las últimas noticias vistas se 
  mantienen al reiniciar la aplicación.

### Propuestas de mejora

En un futuro se podría realizar una ampliación para visualizar en una ventana la noticia al completo al pinchar sobre ella, 
permitiendo la visualización de la imagen junto con el cuerpo de la noticia. Otra mejora sería la de personalización de 
la visualización, permitiendo escoger entre un tema oscuro o claro.

### Referencia a la API
Para más información sobre la API utilizada, puedes consultar la documentación de la misma en [Inshorts News API](https://github.com/cyberboysumanjay/Inshorts-News-API).

### Conclusión y opinión del trabajo realizado

La experiencia de realizar un programa con interfaz gráfico para la optención de datos a través de una API pública ha demostrado 
ser un reto interesante; el uso de JavaFX, si bien algo obtuso al principio, ha demostrado ser intuitivo y limpio en la presentación, 
y la utilización de una API ha servido para demostrar su importancia a la hora del envío de datos e información. En definitiva 
consideramos que el proyecto ha logrado su objetivo de demostrar la utilidad e importancia de los datos almacenados en archivos JSON 
y XML. El proyecto en sí nos tomó unas 12~14 horas y, si bien hay partes que se pueden pulir y mejorar, pensamos que para el 
rango y exigencias es un notable alto.
