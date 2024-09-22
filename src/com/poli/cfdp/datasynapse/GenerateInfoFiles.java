package com.poli.cfdp.datasynapse;

import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Clase para generar archivos de información para un sistema de ventas.
 * Esta clase contiene métodos estáticos para crear archivos de texto
 * con información de vendedores, productos y ventas.
 */
public class GenerateInfoFiles {

    /**
     * Crea un archivo de ventas para un vendedor específico.
     *
     * @param randomSalesCount El número de ventas aleatorias a generar.
     * @param vendedor         Un array con la información del vendedor
     *                         [tipoDocumento, numeroDocumento, nombres, apellidos].
     * @param productos        Una matriz con la información de los productos
     *                         disponibles.
     * @throws Exception Si ocurre un error al escribir el archivo.
     */
    public static void createSalesMenFile(int randomSalesCount, String[] vendedor, String[][] productos) {
        try {
            String nombre = vendedor[2] + " " + vendedor[3];
            File archivoVentas = new File("vendedor_" + nombre.replace(" ", "_") + "_" + vendedor[1] + ".txt");
            try (PrintWriter escritor = new PrintWriter(archivoVentas)) {
                Random random = new Random();

                // Escribe la primera línea con el tipo de documento y número de documento del
                // vendedor
                escritor.println(vendedor[0] + ";" + vendedor[1]);

                // Genera ventas aleatorias
                for (int i = 0; i < randomSalesCount; i++) {
                    String[] producto = productos[random.nextInt(productos.length)];
                    int cantidad = random.nextInt(100) + 1;
                    escritor.printf("%s;%d;%n", producto[0], cantidad);
                }

                System.out.println("Archivo de ventas para " + nombre + " generado exitosamente.");
            }
        } catch (Exception e) {
            System.out.println("Error al generar el archivo de ventas: " + e.getMessage());
        }
    }

    /**
     * Genera un ID de producto corto y aleatorio.
     *
     * @return Un String de 8 caracteres alfanuméricos.
     */
    private static String generateShortProductId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    /**
     * Crea un archivo con la información de los vendedores y devuelve la
     * información generada.
     *
     * @return Una matriz de String con la información de los vendedores generada.
     * @throws Exception Si ocurre un error al escribir el archivo.
     */
    public static String[][] createSalesManInfoFile() {
        String[][] vendedores = new String[5][4];
        try {
            File file = new File("vendedores_info.txt");
            try (PrintWriter writer = new PrintWriter(file)) {
                Random random = new Random();
                String[] nombres = {
                        "Paula Milena;Cagua Gutierrez",
                        "Carlos Arturo;Camargo Morales",
                        "Cristian Camilo;Sabogal López",
                        "Julian Andres;Ortiz Patiño",
                        "Jaime Andres;Trujillo Bautista"
                };
                for (int i = 0; i < nombres.length; i++) {
                    String tipoDocumento = "CC";
                    long numeroDocumento = 100000000L + random.nextInt(900000000);
                    String[] nombreCompleto = nombres[i].split(";");
                    writer.printf("%s;%d;%s;%s%n", tipoDocumento, numeroDocumento, nombreCompleto[0],
                            nombreCompleto[1]);
                    vendedores[i] = new String[] { tipoDocumento, String.valueOf(numeroDocumento), nombreCompleto[0],
                            nombreCompleto[1] };
                }
            }
            System.out.println("Archivo de información de vendedores generado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al generar el archivo de información de vendedores: " + e.getMessage());
        }
        return vendedores;
    }

    public static String[][] createProductsFile(int productCount) {
        String[][] productos = new String[productCount][3];
        try {
            File file = new File("productos.txt");
            try (PrintWriter writer = new PrintWriter(file)) {
                Random random = new Random();
                for (int i = 0; i < productCount; i++) {
                    String id = generateShortProductId();
                    String name = "Producto" + (i + 1);
                    double price = 10.0 + (1000.0 - 10.0) * random.nextDouble();
                    writer.printf("%s;%s;%.2f%n", id, name, price);
                    productos[i] = new String[] { id, name, String.format("%.2f", price) };
                }
            }
            System.out.println("Archivo de productos generado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al generar el archivo de productos: " + e.getMessage());
        }
        return productos;
    }

    /**
     * Método principal que ejecuta la generación de todos los archivos de
     * información.
     */
    public static void main(String[] args) {
        String[][] vendedores = createSalesManInfoFile();
        String[][] productos = createProductsFile(15); // Genera información de 15 productos aleatorios
        for (String[] vendedor : vendedores) {
            createSalesMenFile(10, vendedor, productos); // Genera 10 ventas aleatorias para cada vendedor
        }
    }
}
