package db4o.ex2;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import java.util.List;
import java.util.Scanner;

public class MainDb4o2App {
    private static final String DB_FILE = "tp2.db4o";
    private ObjectContainer db;
    private Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        new MainDb4o2App().run();
    }

    private void run() {
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DB_FILE);
        try {
            menuPrincipal();
        } finally {
            db.close();
            System.out.println("DB cerrada.");
        }
    }

    private void menuPrincipal() {
        while (true) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1) Cliente");
            System.out.println("2) Producto");
            System.out.println("3) Factura");
            System.out.println("4) Detalle (relación factura-producto)");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            String o = sc.nextLine().trim();
            switch (o) {
                case "1": menuEntidad("Cliente"); break;
                case "2": menuEntidad("Producto"); break;
                case "3": menuEntidad("Factura"); break;
                case "4": menuEntidad("Detalle"); break;
                case "0": return;
                default: System.out.println("Opción inválida.");
            }
        }
    }

    private void menuEntidad(String ent) {
        while (true) {
            System.out.println("\n--- " + ent + " ---");
            System.out.println("1) Dar de alta");
            System.out.println("2) Dar de baja");
            System.out.println("3) Listar " + ent);
            System.out.println("0) Volver");
            System.out.print("Opción: ");
            String o = sc.nextLine().trim();
            try {
                if ("Cliente".equals(ent)) {
                    if ("1".equals(o)) altaCliente();
                    else if ("2".equals(o)) bajaCliente();
                    else if ("3".equals(o)) listarClientes();
                    else if ("0".equals(o)) return;
                } else if ("Producto".equals(ent)) {
                    if ("1".equals(o)) altaProducto();
                    else if ("2".equals(o)) bajaProducto();
                    else if ("3".equals(o)) listarProductos();
                    else if ("0".equals(o)) return;
                } else if ("Factura".equals(ent)) {
                    if ("1".equals(o)) altaFactura();
                    else if ("2".equals(o)) bajaFactura();
                    else if ("3".equals(o)) listarFacturas();
                    else if ("0".equals(o)) return;
                } else if ("Detalle".equals(ent)) {
                    if ("1".equals(o)) altaDetalle();
                    else if ("2".equals(o)) bajaDetalle();
                    else if ("3".equals(o)) listarDetalles();
                    else if ("0".equals(o)) return;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Entrada numérica inválida.");
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    // -------- CLIENTE --------
    private void altaCliente() {
        System.out.print("ID cliente: "); int id = Integer.parseInt(sc.nextLine().trim());
        if (buscarCliente(id) != null) { System.out.println("Ya existe cliente " + id); return; }
        System.out.print("Nombre: "); String nom = sc.nextLine().trim();
        Cliente c = new Cliente(id, nom);
        db.store(c); db.commit();
        System.out.println("Cliente creado: " + c);
    }

    private void bajaCliente() {
        System.out.print("ID cliente a eliminar: "); int id = Integer.parseInt(sc.nextLine().trim());
        Cliente c = buscarCliente(id);
        if (c == null) { System.out.println("Cliente no encontrado."); return; }

        // borrar facturas y detalles asociados
        Query qf = db.query(); qf.constrain(Factura.class); qf.descend("cliente").descend("id").constrain(id);
        List<Factura> facs = qf.execute();
        for (Factura f : facs) {
            eliminarFacturaConDetalles(f);
            System.out.println("Eliminada factura asociada: " + f);
        }

        db.delete(c); db.commit();
        System.out.println("Cliente eliminado: " + c);
    }

    private void listarClientes() {
        List<Cliente> list = db.query(Cliente.class);
        if (list.isEmpty()) { System.out.println("No hay clientes."); return; }
        list.forEach(System.out::println);
    }

    // -------- PRODUCTO --------
    private void altaProducto() {
        System.out.print("ID producto: "); int id = Integer.parseInt(sc.nextLine().trim());
        if (buscarProducto(id) != null) { System.out.println("Ya existe producto " + id); return; }
        System.out.print("Descripcion: "); String d = sc.nextLine().trim();
        System.out.print("Stock inicial (entero): "); int stock = Integer.parseInt(sc.nextLine().trim());
        Producto p = new Producto(id, d, stock);
        db.store(p); db.commit();
        System.out.println("Producto creado: " + p);
    }

    private void bajaProducto() {
        System.out.print("ID producto a eliminar: "); int id = Integer.parseInt(sc.nextLine().trim());
        Producto p = buscarProducto(id);
        if (p == null) { System.out.println("Producto no encontrado."); return; }

        // borrar detalles que referencian producto
        Query qd = db.query(); qd.constrain(Detalle.class); qd.descend("producto").descend("id").constrain(id);
        List<Detalle> dets = qd.execute();
        for (Detalle d : dets) {
            db.delete(d);
            System.out.println("Eliminado detalle asociado: " + d);
        }

        db.delete(p); db.commit();
        System.out.println("Producto eliminado: " + p);
    }

    private void listarProductos() {
        List<Producto> list = db.query(Producto.class);
        if (list.isEmpty()) { System.out.println("No hay productos."); return; }
        list.forEach(System.out::println);
    }

    // -------- FACTURA --------
    private void altaFactura() {
        System.out.print("Nro factura: "); int nro = Integer.parseInt(sc.nextLine().trim());
        if (buscarFactura(nro) != null) { System.out.println("Ya existe factura " + nro); return; }

        System.out.print("ID cliente: "); int cid = Integer.parseInt(sc.nextLine().trim());
        Cliente c = buscarCliente(cid);
        if (c == null) { System.out.println("Cliente no encontrado."); return; }

        Factura f = new Factura(nro, c, 0.0);
        db.store(f); db.commit();
        System.out.println("Factura creada: " + f);
    }

    private void bajaFactura() {
        System.out.print("Nro factura a eliminar: "); int nro = Integer.parseInt(sc.nextLine().trim());
        Factura f = buscarFactura(nro);
        if (f == null) { System.out.println("Factura no encontrada."); return; }
        eliminarFacturaConDetalles(f);
        db.commit();
        System.out.println("Factura eliminada: " + f);
    }

    private void listarFacturas() {
        List<Factura> list = db.query(Factura.class);
        if (list.isEmpty()) { System.out.println("No hay facturas."); return; }
        for (Factura f : list) {
            System.out.println(f);
            listarDetallesPorFactura(f.getNro());
        }
    }

    private void eliminarFacturaConDetalles(Factura f) {
        // buscar detalles de esta factura
        Query qd = db.query(); qd.constrain(Detalle.class); qd.descend("factura").descend("nro").constrain(f.getNro());
        List<Detalle> dets = qd.execute();
        for (Detalle d : dets) {
            // restaurar stock
            Producto p = d.getProducto();
            if (p != null) {
                p.setStock(p.getStock() + d.getCantidad());
                db.store(p);
            }
            db.delete(d);
            System.out.println("Eliminado detalle: " + d);
        }
        db.delete(f);
    }

    // -------- DETALLE (sin id propio; clave = (factura.nro, producto.id)) --------
    private void altaDetalle() {
        System.out.print("Nro factura: "); int nro = Integer.parseInt(sc.nextLine().trim());
        Factura f = buscarFactura(nro);
        if (f == null) { System.out.println("Factura no encontrada."); return; }

        System.out.print("ID producto: "); int pid = Integer.parseInt(sc.nextLine().trim());
        Producto p = buscarProducto(pid);
        if (p == null) { System.out.println("Producto no encontrado."); return; }

        System.out.print("Cantidad: "); int cant = Integer.parseInt(sc.nextLine().trim());
        if (cant <= 0) { System.out.println("Cantidad inválida."); return; }

        if (p.getStock() < cant) { System.out.println("Stock insuficiente. Stock actual: " + p.getStock()); return; }

        System.out.print("Precio unitario: "); double pu = Double.parseDouble(sc.nextLine().trim());

        // evitar duplicados (mismo nro+id)
        Detalle ejemplo = new Detalle();
        ejemplo.setFactura(new Factura(nro, null, 0.0));
        ejemplo.setProducto(new Producto(pid, null, 0));
        List<Detalle> exists = db.queryByExample(ejemplo);
        if (!exists.isEmpty()) { System.out.println("Ya existe un detalle para factura " + nro + " y producto " + pid); return; }

        // crear detalle, disminuir stock y actualizar importe de factura
        Detalle d = new Detalle(f, p, cant, pu);
        p.setStock(p.getStock() - cant);
        db.store(p);
        db.store(d);

        // actualizar importe sumando subtotal
        f.setImporte(f.getImporte() + d.subtotal());
        db.store(f);

        db.commit();
        System.out.println("Detalle creado: " + d);
    }

    private void bajaDetalle() {
        System.out.print("Nro factura del detalle: "); int nro = Integer.parseInt(sc.nextLine().trim());
        System.out.print("ID producto del detalle: "); int pid = Integer.parseInt(sc.nextLine().trim());

        Query q = db.query();
        q.constrain(Detalle.class);
        q.descend("factura").descend("nro").constrain(nro);
        q.descend("producto").descend("id").constrain(pid);
        List<Detalle> res = q.execute();
        if (res.isEmpty()) { System.out.println("Detalle no encontrado."); return; }

        Detalle d = res.get(0);
        // restaurar stock
        Producto p = d.getProducto();
        if (p != null) {
            p.setStock(p.getStock() + d.getCantidad());
            db.store(p);
        }
        // actualizar importe factura
        Factura f = d.getFactura();
        if (f != null) {
            f.setImporte(Math.max(0.0, f.getImporte() - d.subtotal()));
            db.store(f);
        }

        db.delete(d);
        db.commit();
        System.out.println("Detalle eliminado: " + d);
    }

    private void listarDetalles() {
        List<Detalle> list = db.query(Detalle.class);
        if (list.isEmpty()) { System.out.println("No hay detalles."); return; }
        list.forEach(System.out::println);
    }

    private void listarDetallesPorFactura(int nro) {
        Query q = db.query();
        q.constrain(Detalle.class);
        q.descend("factura").descend("nro").constrain(nro);
        List<Detalle> res = q.execute();
        if (res.isEmpty()) { System.out.println("   (sin detalles)"); return; }
        for (Detalle d : res) System.out.println("   " + d);
    }

    // -------- HELPERS: búsquedas por ejemplo --------
    private Cliente buscarCliente(int id) {
        Cliente ej = new Cliente(); ej.setId(id);
        List<Cliente> r = db.queryByExample(ej);
        return r.isEmpty() ? null : r.get(0);
    }

    private Producto buscarProducto(int id) {
        Producto ej = new Producto(); ej.setId(id);
        List<Producto> r = db.queryByExample(ej);
        return r.isEmpty() ? null : r.get(0);
    }

    private Factura buscarFactura(int nro) {
        Factura ej = new Factura(); ej.setNro(nro);
        List<Factura> r = db.queryByExample(ej);
        return r.isEmpty() ? null : r.get(0);
    }

    private Detalle buscarDetalle(int nro, int pid) {
        Query q = db.query();
        q.constrain(Detalle.class);
        q.descend("factura").descend("nro").constrain(nro);
        q.descend("producto").descend("id").constrain(pid);
        List<Detalle> res = q.execute();
        return res.isEmpty() ? null : res.get(0);
    }
}