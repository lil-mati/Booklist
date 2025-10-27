<div align="center">

# <img src="https://media.giphy.com/media/zGRFEECuXXIJlXtsur/giphy.gif" width="40"> Booklist

- Aplicación Android nativa que permite gestionar una biblioteca personal mediante operaciones CRUD (Create, Read, Update, Delete) utilizando base de datos SQLite local

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-07405E?style=for-the-badge&logo=sqlite&logoColor=white)
![API](https://img.shields.io/badge/API-21%2B-brightgreen?style=for-the-badge)

</div>

## 🎯 Características Principales

<table>
<tr>
<td width="50%">

### 👤 Gestión de Usuarios
- Registro de usuario
- Sistema de login seguro
- Persistencia de sesión

</td>
<td width="50%">

### 📖 CRUD Completo
- ➕ Agregar libros con detalles
- 📝 Editar información existente
- 🗑️ Eliminar registros
- 📋 Listar biblioteca personal

</td>
</tr>
</table>

## 🏗️ Jerarquia

<pre>
app/
├── manifests/
├── java/
│   └── com.example.sqlite_test/
│       ├── contracts/
│       │   └── DbHelper.java
│       ├── Models/
│       │   ├── libro.java
│       │   └── usuario.java
│       └── Activities/
│           ├── AddBookActivity.java
│           ├── Book.java
│           ├── BookAdapter.java
│           ├── BookListActivity.java
│           ├── LoginActivity.java
│           ├── MainActivity.java
│           └── RegisterActivity.java
├── res/
│   ├── drawable/
│   └── layout/
│       ├── activity_add_book.xml
│       ├── activity_book_list.xml
│       ├── activity_login.xml
│       ├── activity_main.xml
│       ├── activity_register.xml
│       ├── dialog_edit_book.xml
│       └── list_item_book.xml
</pre>

🗄️ Esquema de Base de Datos
---

👥 Tabla usuarios
| Campo | Tipo | Restricción | Descripción |
|:---:|:---:|:---:|---|
| id | INTEGER | PRIMARY KEY AUTOINCREMENT | Identificador único del usuario |
| apellido | TEXT | NOT NULL | Apellido del usuario |
| nombre | TEXT | NOT NULL | Nombre del usuario |
| usuario | TEXT | UNIQUE | Username para autenticación |
| contraseña | TEXT | NOT NULL | Contraseña de acceso |

---

📚 Tabla libros
| Campo | Tipo | Restricción | Descripción |
|:---:|:---:|:---:|---|
| id | INTEGER | PRIMARY KEY AUTOINCREMENT | Identificador único del libro |
| titulo | TEXT | NOT NULL | Título del libro |
| autor | TEXT | | Autor(es) del libro |
| estado | INTEGER | FOREIGN KEY → estado_lectura(id) | Estado actual de lectura |
| fecha_publicacion | TEXT | | Fecha de publicación |
| id_tipo | INTEGER | FOREIGN KEY → tipo_libro(id) | Género/categoría del libro |
| comentario | TEXT | | Notas y comentarios personales |
| id_usuario | INTEGER | FOREIGN KEY → usuarios(id) | Usuario propietario del registro |

---

🗂️ Tabla coleccion
| Campo | Tipo | Restricción | Descripción |
|:---:|:---:|:---:|---|
| id | INTEGER | PRIMARY KEY AUTOINCREMENT | Identificador único de la colección |
| id_usuario | INTEGER | FOREIGN KEY → usuarios(id) | Usuario dueño de la colección |
| id_libro | INTEGER | FOREIGN KEY → libros(id) | Libro incluido en la colección |
| fecha_agregado | TEXT | | Fecha de adición a la colección |
| fecha_terminacion | TEXT | | Fecha de finalización de lectura |

---

🏷️ Tabla tipo_libro
| Campo | Tipo | Restricción | Descripción |
|:---:|:---:|:---:|---|
| id | INTEGER | PRIMARY KEY AUTOINCREMENT | Identificador único del tipo |
| nombre | TEXT | NOT NULL | Nombre del género (ej: Fantasía, Misterio, Romance) |
| descripcion | TEXT | | Descripción detallada del tipo |

Datos Predefinidos:
🧙 Fantasía - Libros de fantasía
🔍 Misterio - Libros de misterio
💕 Romance - Libros de romance

---

📖 Tabla estado_lectura
| Campo | Tipo | Restricción | Descripción |
|:---:|:---:|:---:|---|
| id | INTEGER | PRIMARY KEY AUTOINCREMENT | Identificador único del estado |
| nombre | TEXT | NOT NULL | Nombre del estado de lectura |

Datos Predefinidos:
⏳ Pendiente (ID: 1)
📖 Leyendo (ID: 2)
✅ Leído (ID: 3)

---

## 🚀 Instalación

### Requisitos Previos

<table>
<tr>
<td>

**🛠️ Herramientas**
- Android Studio Arctic Fox+
- JDK 8 o superior
- Git

</td>
<td>

**📱 SDK Android**
- SDK Mínimo: API 21 (Lollipop)
- SDK Target: API 33+
- Gradle 7.0+

</td>
</tr>
</table>

| Categoría | Tecnologías |
|:---:|:---:|
| **Lenguaje** | Java |
| **Base de Datos** | SQLite + SQLiteOpenHelper |
| **UI Components** | RecyclerView, CardView, FloatingActionButton |
| **Patrón** | MVC (Model-View-Controller) |
| **Design** | Material Design Icons, Gradient Drawables |


| Info |
|---|
| Made in Android Studio |
| Tested on Pixel 7 - API 35 |

<img src="https://media.tenor.com/UDC3OVGA1jcAAAAi/icon.gif" width="30"> Work in progress...

---

## <img src="https://media.giphy.com/media/GkD4U3VfiIbzcBhQNu/giphy.gif" width="40"> Developers

<img src="https://media.tenor.com/NAB1czkIZnoAAAAj/dark-souls-artorias.gif" width="40"> Nadia Muñoz as KnightAzuul

<img src="https://media.tenor.com/kR5_CCBbkDkAAAAi/markazushi.gif" width="40"> Matías Mura as lil-mati

<img src="https://media.tenor.com/kDWAb3EF3TgAAAAj/fox-listening-to-music.gif" width="40"> Elias Elo as El14s84

## 
<div align="center">

### ⭐ Si este proyecto te fue útil, considera darle una estrella

**Made with ❤️ for learning**

<img src="https://media.tenor.com/amZ5wxLGUEoAAAAi/hugging-heart-snoopy.gif" width="100">

</div>
