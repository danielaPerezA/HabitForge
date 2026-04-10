# HabitForge 🔥
### *The Adventure of Being You*

HabitForge es una aplicación móvil Android diseñada para ayudar a adultos jóvenes a construir hábitos saludables mediante el seguimiento de rutinas de gimnasio, un sistema de rachas diarias y visualización de progreso.

---

## 📱 Descripción

HabitForge es el frontend móvil (Android) del proyecto **MyOdesy**, desarrollado con **Jetpack Compose** y **Kotlin**. La app permite a los usuarios registrar sus sesiones de entrenamiento, definir metas semanales, mensuales y anuales, y mantener una racha activa que los motive a no fallar.

> Este repositorio contiene únicamente el frontend móvil. El backend (FastAPI + PostgreSQL) se encuentra en un repositorio separado.

---

## ✨ Funcionalidades

### 🏠 Landing
- Página de bienvenida pública con hero section
- Acceso a Login y Registro
- Descripción de las funcionalidades de la app

### 🔐 Autenticación
- Pantalla de Login y Registro en una sola vista con tabs
- Sesión local con `AppSession` (preparada para conectar con backend)
- Validación de contraseñas en el registro
- Nombre de usuario dinámico en toda la app

### 🏋️ Dashboard de Gym
- Racha de entrenamiento con contador diario 🔥
- Marcado de días de la semana con categoría muscular
- Día actual resaltado automáticamente
- Contadores de días entrenados (semana / mes / año)
- Metas configurables (semanal / mensual / anual)
- Barras de progreso mensual animadas (Jan–Dec)
- Barra de meta anual con porcentaje
- Validaciones reales de límites por período
- Feedback visual con Snackbar en cada acción

### 🏠 Home
- Saludo personalizado con nombre del usuario
- Registro de estado de ánimo / nota del día
- Acceso rápido al dashboard de Gym

### 👤 Perfil
- Datos personales editables (nombre, apellidos, edad, teléfono, usuario)
- Metas de gym configurables (semanal y mensual)
- Actualización en tiempo real de la sesión local
- Opción de cerrar sesión con diálogo de confirmación

---

## 🛠️ Stack Técnico

| Tecnología | Uso |
|---|---|
| **Kotlin** | Lenguaje principal |
| **Jetpack Compose** | UI declarativa |
| **Material 3** | Sistema de diseño |
| **ViewModel + StateFlow** | Manejo de estado |
| **Navigation Compose** | Navegación entre pantallas |
| **Hilt** | Inyección de dependencias |
| **Android Studio** | IDE de desarrollo |

---

## 🎨 Diseño

La paleta de colores y el estilo visual están inspirados en **MyOdesy** (versión web):

- **Marrón cálido** `#8B5E3C` — navbar, botones primarios
- **Beige** `#F5ECD7` — fondo principal de pantallas  
- **Crema** `#FAF5EC` — cards, inputs
- **Naranja fuego** `#E8593C` — rachas activas, mes actual
- Tipografía: **Lora** (serif display) + **Nunito** (sans legible)

---

## 📂 Estructura del Proyecto

```
app/src/main/java/com/example/habitforge/app/
├── navegation/
│   └── Navegation.kt          # Rutas y NavHost
├── session/
│   └── AppSession.kt          # Sesión local del usuario
├── ui/
│   ├── auth/
│   │   └── LoginScreen.kt     # Login + Register (tabs)
│   ├── gym/
│   │   ├── GymScreen.kt       # Dashboard de gym
│   │   └── GymViewModel.kt    # Lógica e interactividad
│   ├── home/
│   │   └── HomeScreen.kt      # Home post-login
│   ├── landing/
│   │   └── LandingScreen.kt   # Página pública
│   ├── profile/
│   │   └── ProfileScreen.kt   # Perfil de usuario
│   ├── theme/
│   │   ├── Theme.kt           # Colores y tema
│   │   ├── Typography.kt      # Tipografía
│   │   └── Shapes.kt          # Formas y tokens
│   └── MainScreen.kt          # Scaffold + Bottom Nav
├── HabitForgeApp.kt           # Application class (Hilt)
└── MainActivity.kt            # Entry point
```

---

## 🚀 Cómo ejecutar el proyecto

### Requisitos
- Android Studio Hedgehog o superior
- JDK 11+
- Dispositivo físico o emulador con Android 8.0+ (API 26+)

### Pasos

1. Clona el repositorio:
```bash
git clone https://github.com/danielaPerezA/HabitForge.git
cd HabitForge
git checkout feature/danielaPerezA
```

2. Abre el proyecto en Android Studio

3. Haz **Sync Now** cuando Android Studio lo solicite

4. Conecta tu dispositivo o inicia un emulador

5. Presiona ▶ para ejecutar

---

## 🔗 Repositorios relacionados

| Proyecto | Repositorio |
|---|---|
| **Frontend Web** (Angular) | MyOdesy Web |
| **Backend** (FastAPI + PostgreSQL) | MyOdesy Backend |

---

## 👩‍💻 Desarrolladora

**Daniela Pérez Agualimpia**  
Desarrollo Full-Stack Mobile  
Laboratorio de Software — 2025

---

> *"The Adventure of Being You"* 🌱