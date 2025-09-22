import Modelo.*;
import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("tapas");
        EntityManager em = emf.createEntityManager();
        Scanner sc = new Scanner(System.in);

        boolean salir = false;

        while (!salir) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Programador");
            System.out.println("2. Proyecto");
            System.out.println("3. Lenguaje");
            System.out.println("4. Relación Programador-Proyecto (P_P)");
            System.out.println("5. Relación Programador-Lenguaje (P_I)");
            System.out.println("0. Salir");
            System.out.print("Elige una tabla: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> menuProgramador(em, sc);
                case 2 -> menuProyecto(em, sc);
                case 3 -> menuLenguaje(em, sc);
                case 4 -> menuPP(em, sc);
                case 5 -> menuPI(em, sc);
                case 0 -> salir = true;
                default -> System.out.println("Opción no válida.");
            }
        }

        em.close();
        emf.close();
    }

    // ====== PROGRAMADOR ======
    private static void menuProgramador(EntityManager em, Scanner sc) {
        System.out.println("\n--- PROGRAMADOR ---");
        System.out.println("1. Alta");
        System.out.println("2. Baja");
        System.out.println("3. Consultar");
        System.out.println("4. Listar todos");
        System.out.print("Elige una acción: ");
        int op = sc.nextInt(); sc.nextLine();

        switch (op) {
            case 1 -> {
                em.getTransaction().begin();
                Programador prog = new Programador();
                System.out.print("ID: ");
                prog.setId(sc.nextInt()); sc.nextLine();
                System.out.print("Nombre: ");
                prog.setNombre(sc.nextLine());
                System.out.print("Saldo: ");
                prog.setSaldo(sc.nextInt()); sc.nextLine();
                em.persist(prog);
                em.getTransaction().commit();
                System.out.println("Programador agregado.");
            }
            case 2 -> {
                em.getTransaction().begin();
                System.out.print("ID a borrar: ");
                int idBorrar = sc.nextInt(); sc.nextLine();
                Programador progBorrar = em.find(Programador.class, idBorrar);
                if (progBorrar != null) {
                    em.remove(progBorrar);
                    System.out.println("Programador eliminado.");
                } else {
                    System.out.println("No existe ese ID.");
                }
                em.getTransaction().commit();
            }
            case 3 -> {
                System.out.print("ID a consultar: ");
                int idConsultar = sc.nextInt(); sc.nextLine();
                Programador progConsulta = em.find(Programador.class, idConsultar);
                if (progConsulta != null) {
                    System.out.println("ID: " + progConsulta.getId());
                    System.out.println("Nombre: " + progConsulta.getNombre());
                    System.out.println("Saldo: " + progConsulta.getSaldo());
                } else {
                    System.out.println("No existe ese ID.");
                }
            }
            case 4 -> {
                List<Programador> lista = em.createQuery("SELECT p FROM Programador p", Programador.class).getResultList();
                for (Programador p : lista) {
                    System.out.println(p.getId() + " | " + p.getNombre() + " | Saldo: " + p.getSaldo());
                }
            }
        }
    }

    // ====== PROYECTO ======
    private static void menuProyecto(EntityManager em, Scanner sc) {
        System.out.println("\n--- PROYECTO ---");
        System.out.println("1. Alta");
        System.out.println("2. Baja");
        System.out.println("3. Consultar");
        System.out.println("4. Listar todos");
        System.out.print("Elige una acción: ");
        int op = sc.nextInt(); sc.nextLine();

        switch (op) {
            case 1 -> {
                em.getTransaction().begin();
                Proyecto proj = new Proyecto();
                System.out.print("ID: ");
                proj.setId(sc.nextInt()); sc.nextLine();
                System.out.print("Descripción: ");
                proj.setDescripcion(sc.nextLine());
                System.out.print("Presupuesto: ");
                proj.setPresupuesto(sc.nextInt()); sc.nextLine();
                em.persist(proj);
                em.getTransaction().commit();
                System.out.println("Proyecto agregado.");
            }
            case 2 -> {
                em.getTransaction().begin();
                System.out.print("ID a borrar: ");
                int idBorrar = sc.nextInt(); sc.nextLine();
                Proyecto projBorrar = em.find(Proyecto.class, idBorrar);
                if (projBorrar != null) {
                    em.remove(projBorrar);
                    System.out.println("Proyecto eliminado.");
                } else {
                    System.out.println("No existe ese ID.");
                }
                em.getTransaction().commit();
            }
            case 3 -> {
                System.out.print("ID a consultar: ");
                int idConsultar = sc.nextInt(); sc.nextLine();
                Proyecto projConsulta = em.find(Proyecto.class, idConsultar);
                if (projConsulta != null) {
                    System.out.println("ID: " + projConsulta.getId());
                    System.out.println("Descripción: " + projConsulta.getDescripcion());
                    System.out.println("Presupuesto: " + projConsulta.getPresupuesto());
                } else {
                    System.out.println("No existe ese ID.");
                }
            }
            case 4 -> {
                List<Proyecto> lista = em.createQuery("SELECT p FROM Proyecto p", Proyecto.class).getResultList();
                for (Proyecto p : lista) {
                    System.out.println(p.getId() + " | " + p.getDescripcion() + " | Presupuesto: " + p.getPresupuesto());
                }
            }
        }
    }

    // ====== LENGUAJE ======
    private static void menuLenguaje(EntityManager em, Scanner sc) {
        System.out.println("\n--- LENGUAJE ---");
        System.out.println("1. Alta");
        System.out.println("2. Baja");
        System.out.println("3. Consultar");
        System.out.println("4. Listar todos");
        System.out.print("Elige una acción: ");
        int op = sc.nextInt(); sc.nextLine();

        switch (op) {
            case 1 -> {
                em.getTransaction().begin();
                Lenguaje lang = new Lenguaje();
                System.out.print("ID: ");
                lang.setId(sc.nextInt()); sc.nextLine();
                System.out.print("Nombre: ");
                lang.setNombre(sc.nextLine());
                em.persist(lang);
                em.getTransaction().commit();
                System.out.println("Lenguaje agregado.");
            }
            case 2 -> {
                em.getTransaction().begin();
                System.out.print("ID a borrar: ");
                int idBorrar = sc.nextInt(); sc.nextLine();
                Lenguaje langBorrar = em.find(Lenguaje.class, idBorrar);
                if (langBorrar != null) {
                    em.remove(langBorrar);
                    System.out.println("Lenguaje eliminado.");
                } else {
                    System.out.println("No existe ese ID.");
                }
                em.getTransaction().commit();
            }
            case 3 -> {
                System.out.print("ID a consultar: ");
                int idConsultar = sc.nextInt(); sc.nextLine();
                Lenguaje langConsulta = em.find(Lenguaje.class, idConsultar);
                if (langConsulta != null) {
                    System.out.println("ID: " + langConsulta.getId());
                    System.out.println("Nombre: " + langConsulta.getNombre());
                } else {
                    System.out.println("No existe ese ID.");
                }
            }
            case 4 -> {
                List<Lenguaje> lista = em.createQuery("SELECT l FROM Lenguaje l", Lenguaje.class).getResultList();
                for (Lenguaje l : lista) {
                    System.out.println(l.getId() + " | " + l.getNombre());
                }
            }
        }
    }

    // ====== P_P (Programador-Proyecto) ======
    private static void menuPP(EntityManager em, Scanner sc) {
        System.out.println("\n--- PROGRAMADOR - PROYECTO ---");
        System.out.println("1. Alta");
        System.out.println("2. Baja");
        System.out.println("3. Consultar");
        System.out.println("4. Listar todos");
        System.out.print("Elige una acción: ");
        int op = sc.nextInt(); sc.nextLine();

        switch (op) {
            case 1 -> {
                em.getTransaction().begin();
                PP pp = new PP();
                PPId id = new PPId();

                System.out.print("ID Programador: ");
                id.setIdProgramador(sc.nextInt());
                System.out.print("ID Proyecto: ");
                id.setIdProyecto(sc.nextInt());
                sc.nextLine();

                pp.setId(id);
                pp.setProgramador(em.find(Programador.class, id.getIdProgramador()));
                pp.setProyecto(em.find(Proyecto.class, id.getIdProyecto()));

                System.out.print("Horas semanales: ");
                pp.setHorasSemanales(sc.nextInt()); sc.nextLine();

                em.persist(pp);
                em.getTransaction().commit();
                System.out.println("Relación Programador-Proyecto agregada.");
            }
            case 2 -> {
                em.getTransaction().begin();
                PPId id = new PPId();
                System.out.print("ID Programador: ");
                id.setIdProgramador(sc.nextInt());
                System.out.print("ID Proyecto: ");
                id.setIdProyecto(sc.nextInt());
                sc.nextLine();

                PP pp = em.find(PP.class, id);
                if (pp != null) {
                    em.remove(pp);
                    System.out.println("Relación eliminada.");
                } else {
                    System.out.println("No existe esa relación.");
                }
                em.getTransaction().commit();
            }
            case 3 -> {
                PPId id = new PPId();
                System.out.print("ID Programador: ");
                id.setIdProgramador(sc.nextInt());
                System.out.print("ID Proyecto: ");
                id.setIdProyecto(sc.nextInt());
                sc.nextLine();

                PP pp = em.find(PP.class, id);
                if (pp != null) {
                    System.out.println("Programador: " + pp.getProgramador().getNombre());
                    System.out.println("Proyecto: " + pp.getProyecto().getDescripcion());
                    System.out.println("Horas: " + pp.getHorasSemanales());
                } else {
                    System.out.println("No existe esa relación.");
                }
            }
            case 4 -> {
                List<PP> lista = em.createQuery("SELECT r FROM PP r", PP.class).getResultList();
                for (PP r : lista) {
                    System.out.println("Prog: " + r.getProgramador().getNombre()
                            + " | Proy: " + r.getProyecto().getDescripcion()
                            + " | Horas: " + r.getHorasSemanales());
                }
            }
        }
    }

    // ====== P_I (Programador-Lenguaje) ======
    private static void menuPI(EntityManager em, Scanner sc) {
        System.out.println("\n--- PROGRAMADOR - LENGUAJE ---");
        System.out.println("1. Alta");
        System.out.println("2. Baja");
        System.out.println("3. Consultar");
        System.out.println("4. Listar todos");
        System.out.print("Elige una acción: ");
        int op = sc.nextInt(); sc.nextLine();

        switch (op) {
            case 1 -> {
                em.getTransaction().begin();
                PI pi = new PI();
                PIId id = new PIId();

                System.out.print("ID Programador: ");
                id.setIdProgramador(sc.nextInt());
                System.out.print("ID Lenguaje: ");
                id.setIdLenguaje(sc.nextInt());
                sc.nextLine();

                pi.setId(id);
                pi.setProgramador(em.find(Programador.class, id.getIdProgramador()));
                pi.setLenguaje(em.find(Lenguaje.class, id.getIdLenguaje()));

                System.out.print("Nivel: ");
                pi.setNivel(sc.nextInt()); sc.nextLine();

                em.persist(pi);
                em.getTransaction().commit();
                System.out.println("Relación Programador-Lenguaje agregada.");
            }
            case 2 -> {
                em.getTransaction().begin();
                PIId id = new PIId();
                System.out.print("ID Programador: ");
                id.setIdProgramador(sc.nextInt());
                System.out.print("ID Lenguaje: ");
                id.setIdLenguaje(sc.nextInt());
                sc.nextLine();

                PI pi = em.find(PI.class, id);
                if (pi != null) {
                    em.remove(pi);
                    System.out.println("Relación eliminada.");
                } else {
                    System.out.println("No existe esa relación.");
                }
                em.getTransaction().commit();
            }
            case 3 -> {
                PIId id = new PIId();
                System.out.print("ID Programador: ");
                id.setIdProgramador(sc.nextInt());
                System.out.print("ID Lenguaje: ");
                id.setIdLenguaje(sc.nextInt());
                sc.nextLine();

                PI pi = em.find(PI.class, id);
                if (pi != null) {
                    System.out.println("Programador: " + pi.getProgramador().getNombre());
                    System.out.println("Lenguaje: " + pi.getLenguaje().getNombre());
                    System.out.println("Nivel: " + pi.getNivel());
                } else {
                    System.out.println("No existe esa relación.");
                }
            }
            case 4 -> {
                List<PI> lista = em.createQuery("SELECT r FROM PI r", PI.class).getResultList();
                for (PI r : lista) {
                    System.out.println("Prog: " + r.getProgramador().getNombre()
                            + " | Leng: " + r.getLenguaje().getNombre()
                            + " | Nivel: " + r.getNivel());
                }
            }
        }
    }
}


