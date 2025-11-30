# Kotlin Product Banner App

This is a small Android app made with **Kotlin** and **Jetpack Compose**.  
The app shows a simple product banner on the screen.

## What the app shows

The banner contains:

- A **product image** in the background
- **Product name:** Aurora Wireless Headphones
- **Company name:** NorthSound Audio
- **Contact information:**
  - Email: `support@northsound.com` #  random 
  - Phone: `+358 40 123 4567`        #  random

All the text is placed in a card in the center of the screen, on top of
the background image, with padding and alignment so it looks clean.

## Files in this folder

- `Main.kt`  
  Contains the `MainActivity` class and the `ProductBannerScreen`
  composable function that builds the UI.

- `screenshot.png`  
  Screenshot of the app running on a device, showing the product banner.

- `README.md`  
  This description of the assignment.

## How it works (short)

- The UI is built using Jetpack Compose.
- A background image is loaded with `Image` and `painterResource`.
- A `Card` is used to show the product name, company name and contact info.
- The layout uses `Box` and `Column` with spacing and alignment.
