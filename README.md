# Proyecto UD 2. Acceso a Datos
## Iago Oitavén & Ángel Monroy

### Introducción al Proyecto y Base de Datos

El proyecto que hemos desenvuelto consiste en una aplicación que obtiene y muestra al usuario noticias en relación a 
la categoría que este haya seleccionado previamente. Para ello contamos con dos bases de datos  en las cuales guardamos a los usuarios que pueden acceder a la aplicación y las noticias. De esta última obtenemos los datos que son mostrados al usuario en formato de tabla con la cual puede interactuar para 
ver la imágen asociada a la noticia, copiar la URL de esta o selecionar todas o un grupo de ellas para guardar en formato 
binario, JSON, XML o texto plano. A su vez las noticias guardadas en XML, JSON o binario pueden volver a ser cargadas a 
la aplicación para ser visualizadas. El programa también permite al usuario mantener su última sesión seleccionando 
una check box, lo cual hace que una vez vuelva a abrirlo restaure las últimas noticias que haya visto.

### Manual técnico

El proyecto compartimentaliza la vista, la lógica y las funciones que controlen las acciones como acceso guardado o carga 
a archivos, siguiendo este modelo la vista se comunica con la lógica, que a su vez llama a las funciones de control 
en caso de requerirlo. Tras lo cual la vista se actualiza con lo que la lógica le devuelva, creando, así gracias a este 
sistema, una encapsulación del codigo que lo hace fácil de mantener y ampliar. En sí el código se divide en tres paquetes: 
view, donde se almacena la lógica y relación con la interfaz gráfica en forma de dos clases: MainView y Relational; model, 
donde se encuentran las clases Config, News y RawNews (el formato en el que se guardan los conjuntos de noticias) y mgmt, 
donde se encuentran Log: para la creación de archivos log, Request: para las solicitudes a las bases de datos, y FileUtils: que se encarga 
de la modificación de ficheros.
Para clonar el repositorio:
```
git clone https://github.com/CGAInstitution/proyectoud1-angel-iago
```
Estructura de directorios:
```
apirequests.proj_u1/     # Directorio principal donde están todas las clases
|- Main.java             # Inicio y carga de la configuración guardada
|- mgmt/                 # Directorio para gestinar las clases relacionadas con uso de ficheros y peticiones a las bases de datos
|    └─ FileUtils.java   # Gestión de todo lo relacionado con escritura y lectura de ficheros y configuración
|    └─ Log.java         # Escritura de un log de errores
|    └─ Request.java     # Realiza las peticiones a las bases de datos
|- model/                # Directorio con los modelos de datos y configuración
|    └─ Config.java      # Gestiona la configuración inicial y el autoguardado de la ultima sesión 
|    └─ News.java        # Clase para los atributos de cada noticia
|    └─ RawNews.java     # Modelo adaptado para mappear la respuesta de la base de datos
|- view/                 # Directorio para gestión de la interfaz gráfica
     └─ MainView.java    # Clase que gestiona todos los elementos que contiene la vista
     └─ Relational       # Controlador, prepara los datos para mostrar
```

Para ejecutar el JAR, incluye este en el directorio principal del proyecto. Si no se ejecuta, escribe el siguiente comando:
```shell
java -jar proyectoud1-angel-iago.jar
```


### Manual de Usuario

Antes de iniciar la aplicación se debería cargar el script que se adjunta, usando MySQL, con datos de prueba para poder visualizar
cómo funcionaría en un caso real la aplicación. Este se puede ejecutar desde el Workbench o desde terminal con:
```
mysql -u [nombreDeUsuario] -p [Contraseña]
mysql source [elScript].sql;
```

Tras eso se tiene que hacer login usando las siguientes credenciales:
| Usuario | Contraseña |
|  :---:  |    :---:   |
| admin   | admin      |

En sí la utilización de esta aplicación es de lo más sencilla e intuitiva. Al iniciar el programa el usuario 
podrá buscar las noticias en la base de datos filtradas por categoría mediante el botón de búsqueda, cargar noticias
guardadas o guardar las que puede ver en varios formatos. Otra cosa que puede hacer es guardar una nueva noticia
 en la base de datos con el boton correspondiente.

![Inicio](https://user-images.githubusercontent.com/105040748/200887301-f49311c5-c01b-442b-994b-7192b2c92d81.PNG)
> Inicio del programa

Una vez cargadas el usuario podrá copiar la URL para ver la noticia en la web, abrir la imagen de 
un artículo para visualizarla o guardar todas o el grupo que seleccione de noticias en un fichero binario, XML, JSON o de 
texto plano. Habiendo hecho esto y teniendo un archivo de noticias al usuario se le presenta la opción de, seleccionando el 
boton de cargar noticias, mostrando las noticias guardadas.


![GuardarNoticia](https://user-images.githubusercontent.com/105040748/200889437-dd8e6411-0844-4cd8-8088-9080071debbe.PNG)
> Creación de una nueva noticia

Para guardarla se han de rellenar los campos que aparecen

![Noticia](https://user-images.githubusercontent.com/105040748/200888929-91f0d75a-1fcf-4d4a-89a6-6636559b652f.PNG)
> Actualización o borrado de una noticia

En lo relativo a la usabilidad, podemos: haciendo click derecho sobre una noticia copiar la URL a la fuente,
con doble click derecho acceder al menú para modificarla o borrarla y con doble click izquierdo se abre una nueva ventana 
para leer el resumen de la misma con comodidad.

### Reparto de tareas

En este proyecto Iago Oitavén se encargo de la organización, estructura, interfaz grafica, lógica y petición a la base de datos; 
mientras que Ángel Monroy realizo la parte de control donde se encuentran los métodos de guardado y cargado de noticias, 
fichero de configuración, log para la creacion de ficheros log, implementacion del sistema de carga y peticiones a la base de datos.
Ambos modificaron y vieron el código de otro permitiendoles obtener un conocimiento general de la aplicación que les sirvió 
para ayudarse entre ellos. Puesto en porcentaje Iago Oitaven contribuyó un 55% mientras que Ángel Monroy un 45% 

### Extras

* Control de errores - En caso de error se crea un archivo log con la información del error para que el usuario visualice.
  Además de multiples avisos durante el uso de la aplicación mostrando al usuario de la correcta realización de las acciones.
* Uso de la aplicación, accesibilidad - Habiendo guardado antes las noticias en fichero o selecionando mantener la última sesión se pueden 
  volver a visualizar las noticias.
* Almacenamiento de último estado - Seleccionando la check box para mantener la sesión las últimas noticias vistas se 
  mantienen al reiniciar la aplicación.
* Log in de usuarios, encriptando la contraseña antes de enviarla y guardarla en la base de datos para proporcionar seguridad

### Propuestas de mejora

En un futuro se podría realizar una ampliación para visualizar en una ventana la noticia al completo al pinchar sobre ella, 
permitiendo la visualización de la imagen junto con el cuerpo de la noticia. Otra mejora sería la de personalización de 
la visualización, permitiendo escoger entre un tema oscuro o claro.

### Conclusión y opinión del trabajo realizado

La experiencia de realizar un programa con interfaz gráfico para la optención de datos a través de una base de datos ha demostrado 
ser un reto interesante; el uso de JavaFX, si bien algo obtuso al principio, ha demostrado ser intuitivo y limpio en la presentación, 
y la utilización de las bases de datos ha servido para demostrar su importancia a la hora de la gestión de datos e información. En definitiva 
consideramos que el proyecto ha logrado su objetivo de demostrar la utilidad e importancia de los datos almacenados en bases de datos. 
El proyecto en sí nos tomó unas 12~14 horas y, si bien hay partes que se pueden pulir y mejorar, pensamos que para el rango y exigencias 
es un notable alto.
