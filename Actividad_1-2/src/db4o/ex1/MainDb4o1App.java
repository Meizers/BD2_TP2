package db4o.ex1;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import java.util.List;
import java.util.Scanner;

public class MainDb4o1App {
    private static final String DB_FILE = "tp1.db4o";
    private ObjectContainer db;
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new MainDb4o1App().run();
    }

    private void run() {
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DB_FILE);
        try {
            menuLoop();
        } finally {
            db.close();
            System.out.println("Base de datos cerrada. Adiós!");
        }
    }

    private void menuLoop() {
        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1) Alta cliente");
            System.out.println("2) Baja cliente");
            System.out.println("3) Listar clientes");
            System.out.println("4) Alta factura");
            System.out.println("5) Baja factura");
            System.out.println("6) Listar facturas");
            System.out.println("7) Listar facturas por cliente");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            String opt = scanner.nextLine().trim();
            switch (opt) {
                case "1": altaCliente(); break;
                case "2": bajaCliente(); break;
                case "3": listarClientes(); break;
                case "4": altaFactura(); break;
                case "5": bajaFactura(); break;
                case "6": listarFacturas(); break;
                case "7": listarFacturasPorCliente(); break;
                case "0": return;
                default: System.out.println("Opción inválida.");
            }
        }
    }

    // --- Clientes ---
    private void altaCliente() {
        try {
            System.out.print("ID cliente (entero): ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            if (buscarClientePorId(id) != null) {
                System.out.println("Ya existe cliente con id=" + id);
                return;
            }
            System.out.print("Nombre / descripción: ");
            String descr = scanner.nextLine().trim();
            Cliente c = new Cliente(id, descr);
            db.store(c);
            db.commit();
            System.out.println("Cliente creado: " + c);
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private void bajaCliente() {
        try {
            System.out.print("ID cliente a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Cliente c = buscarClientePorId(id);
            if (c == null) {
                System.out.println("No existe cliente con id=" + id);
                return;
            }
            // eliminar facturas asociadas
            Query q = db.query();
            q.constrain(Factura.class);
            q.descend("cliente").descend("id").constrain(id);
            List<Factura> facturas = q.execute();
            for (Factura f : facturas) {
                db.delete(f);
                System.out.println("Eliminada factura: " + f);
            }
            db.delete(c);
            db.commit();
            System.out.println("Cliente eliminado: " + c);
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private void listarClientes() {
        List<Cliente> clientes = db.query(Cliente.class);
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes.");
            return;
        }
        System.out.println("=== Clientes ===");
        for (Cliente c : clientes) System.out.println(c);
    }

    private Cliente buscarClientePorId(int id) {
        Cliente ejemplo = new Cliente();
        ejemplo.setId(id);
        List<Cliente> res = db.queryByExample(ejemplo);
        return res.isEmpty() ? null : res.get(0);
    }

    // --- Facturas ---
    private void altaFactura() {
        try {
            System.out.print("Nro factura (entero): ");
            int nro = Integer.parseInt(scanner.nextLine().trim());
            if (buscarFacturaPorNro(nro) != null) {
                System.out.println("Ya existe factura con nro=" + nro);
                return;
            }
            System.out.print("ID cliente asociado: ");
            int cid = Integer.parseInt(scanner.nextLine().trim());
            Cliente c = buscarClientePorId(cid);
            if (c == null) {
                System.out.println("No existe cliente con id=" + cid + ". Crea el cliente primero.");
                return;
            }
            System.out.print("Importe (ej: 1234.56): ");
            double imp = Double.parseDouble(scanner.nextLine().trim());
            Factura f = new Factura(nro, c, imp);
            db.store(f);
            db.commit();
            System.out.println("Factura creada: " + f);
        } catch (NumberFormatException e) {
            System.out.println("Entrada numérica inválida.");
        }
    }

    private void bajaFactura() {
        try {
            System.out.print("Nro factura a eliminar: ");
            int nro = Integer.parseInt(scanner.nextLine().trim());
            Factura f = buscarFacturaPorNro(nro);
            if (f == null) {
                System.out.println("No existe factura con nro=" + nro);
                return;
            }
            db.delete(f);
            db.commit();
            System.out.println("Factura eliminada: " + f);
        } catch (NumberFormatException e) {
            System.out.println("Número inválido.");
        }
    }

    private void listarFacturas() {
        List<Factura> facturas = db.query(Factura.class);
        if (facturas.isEmpty()) {
            System.out.println("No hay facturas.");
            return;
        }
        System.out.println("=== Facturas ===");
        for (Factura f : facturas) System.out.println(f);
    }

    private void listarFacturasPorCliente() {
        try {
            System.out.print("ID cliente: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Query q = db.query();
            q.constrain(Factura.class);
            q.descend("cliente").descend("id").constrain(id);
            List<Factura> res = q.execute();
            if (res.isEmpty()) {
                System.out.println("No hay facturas para el cliente id=" + id);
                return;
            }
            System.out.println("=== Facturas del cliente id=" + id + " ===");
            for (Factura f : res) System.out.println(f);
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private Factura buscarFacturaPorNro(int nro) {
        Factura ejemplo = new Factura();
        ejemplo.setNro(nro);
        List<Factura> res = db.queryByExample(ejemplo);
        return res.isEmpty() ? null : res.get(0);
    }
}
