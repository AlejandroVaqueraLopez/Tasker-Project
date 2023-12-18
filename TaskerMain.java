import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
/*
Proyecto final: Tasker
Materia: Programacion Orientada a Objetos
Alumno: Alejandro Vaquera Lopez
Descripcion: Sistema para crear, consultar y eliminar tareas en un tablero
*/

interface TaskTicket {
    public void CreateTask(Task newTask);
    public boolean RemoveTask(int ID);
}

class Animation {
    public static void PrintMsgWithProgressBar(String message, int length, long timeInterval)
        {
            StringBuilder builder = new StringBuilder();
            Stream.generate(() -> ".").limit(length).forEach(builder::append);
            System.out.println(message);
            for(int i = 0; i < length; i++)
            {
                builder.replace(i,i+1,"=");
                String progressBar = "\r"+builder;
                System.out.print(progressBar);
                try
                {
                    Thread.sleep(timeInterval);
                }
                catch (InterruptedException ignored)
                {
                    System.out.println("Hubo un problema tecnico con la animacion!" + ignored);
                }
            }
            System.out.print(">>");
        }
}

class Board implements TaskTicket{
    public String RESET = "\u001B[0m";
    public String RED = "\u001B[31m";
    public String GREEN = "\u001B[32m";
    public String YELLOW = "\u001B[33m";
    public String MAGENTA = "\u001B[35m";
    public String CYAN = "\u001B[36m"; 

    private List<Task> tasks = new ArrayList<>();

    public List<Task> GetTaskArray(){
        return this.tasks;
    }
    @Override
    public void CreateTask(Task newTask){
      this.tasks.add(newTask);
    }

    public Task AccessTask(int ID){
        Task access = null;
        for(int cont = 0; cont < this.tasks.size(); cont++){
            if(this.GetTaskArray().get(cont).GetID() == ID){
                access = this.GetTaskArray().get(cont);
            }
        }
        return access;
    }

    @Override
    public boolean RemoveTask(int ID){
        boolean response = false;
        for(int cont = 0; cont < this.tasks.size(); cont++){
            if(this.GetTaskArray().get(cont).GetID() == ID){
                this.GetTaskArray().remove(cont);
                response = true;
            }else{
                response = false;
            }
        }
        return response;
    }

}

class Task{
    private int _ID = 0;
    private String _title = "";
    private String _body = "";
    private String _dueDate = "";

    public Task(int ID, String title, String body, String dueDate){
        this._ID = ID;
        this._title = title;
        this._body = body;
        this._dueDate = dueDate;
    }

    public Task CreateTask(int ID, String title, String body, String dueDate){
        Task newTask = new Task(ID, title, body, dueDate);
        return newTask;
    }

    public int GetID(){
        return this._ID;
    }
    public String GetName(){
        return this._title;
    }
    public String GetBody(){
        return this._body;
    }
    public String GetDueDate(){
        return this._dueDate;
    }
    public void SetDueDate(String newDueDate){
        this._dueDate = newDueDate;
    }
    public void SetBody(String newBody){
        this._body = newBody;
    }
    public int CreateID(){
        return this._ID++;
    }

}

public class TaskerMain {
    

    public static void main(String[] args){

        int opc = 0;
        Task taskInstance = new Task(0, null,null,null);
        Board boardInstance = new Board();

        do{
            System.out.println("\n\n");

            Animation.PrintMsgWithProgressBar((boardInstance.GREEN + "Cargando lista" + boardInstance.CYAN) , 85, 8);

            System.out.println(boardInstance.YELLOW + "\n=======================================Lista de tareas===========================================" + boardInstance.RESET);
            System.out.println(boardInstance.CYAN + "[ID]" + boardInstance.MAGENTA + " TITLE " + " " + boardInstance.RED + "DUE" + boardInstance.RESET);
           
            for(int cont = 0; cont < boardInstance.GetTaskArray().size(); cont++){
                Task item = boardInstance.GetTaskArray().get(cont);
                System.out.println(boardInstance.CYAN + "[" + item.GetID() + "] " + boardInstance.MAGENTA + item.GetName() + " " + boardInstance.RED + item.GetDueDate() + boardInstance.RESET);
            }

            System.out.println(boardInstance.YELLOW + "----------------------------------------------------------------------------------------------------" + boardInstance.RESET);
            System.out.println(boardInstance.CYAN + "Menu de opcion" + boardInstance.RESET);
            System.out.println("1. Crear tarea");
            System.out.println("2. Acceder a tarea");
            System.out.println("3. Salir");
            System.out.println(boardInstance.YELLOW + "----------------------------------------------------------------------------------------------------" + boardInstance.RESET);

            System.out.println(boardInstance.CYAN + "Ingrese la opcion: ");
            Scanner input = new Scanner(System.in);
            opc = input.nextInt();

            switch (opc) {
                case 1:
                    System.out.println(boardInstance.YELLOW + "\n----------------------------------------------------------------------------------------------------" + boardInstance.RESET);
                    System.out.println(boardInstance.CYAN + "Crear tarea" + boardInstance.RESET);
                    System.out.println(boardInstance.YELLOW + "Asignar titulo: " + boardInstance.RESET);
                        Scanner name = new Scanner(System.in);
                        String newTitle = name.nextLine();
                    System.out.println(boardInstance.YELLOW + "Agregar contenido: " + boardInstance.RESET);
                        Scanner body = new Scanner(System.in);
                        String newBody = body.nextLine();
                    System.out.println(boardInstance.YELLOW + "Ingresar fecha de vencimiento" + boardInstance.RESET);
                        Scanner dueDate = new Scanner(System.in);
                        String newDueDate = dueDate.nextLine();
                    System.out.println(boardInstance.GREEN + "\nTarea creada!" + boardInstance.RESET);
                    System.out.println(boardInstance.YELLOW + "\n----------------------------------------------------------------------------------------------------" + boardInstance.RESET);

                    taskInstance.CreateID();
                    int newID = taskInstance.GetID();
                    Task newItem = taskInstance.CreateTask(newID, newTitle, newBody, newDueDate);

                    boardInstance.CreateTask(newItem);
                    break;

                case 2:
                    System.out.println(boardInstance.YELLOW + "\n----------------------------------------------------------------------------------------------------" + boardInstance.RESET);
                    System.out.println(boardInstance.CYAN + "Acceder a tarea" + boardInstance.RESET);
                    System.out.println("Ingresar el ID: ");
                        Scanner inputID = new Scanner(System.in);
                        int accessID = inputID.nextInt();

                        Task filtered = boardInstance.AccessTask(accessID);
                    
                    if(filtered != null){
                        int subOpc = 0;
                        do{
                            System.out.println(boardInstance.YELLOW + "\n----------------------------------------------------------------------------------------------------" + boardInstance.RESET);
                            System.out.println(boardInstance.CYAN + "Datos de la tarea: " + boardInstance.RESET);
                            System.out.println(boardInstance.MAGENTA + "Nombre: " + boardInstance.RESET + filtered.GetName());
                            System.out.println(boardInstance.GREEN + "Contenido: " + boardInstance.RESET + filtered.GetBody());
                            System.out.println(boardInstance.RED + "Fecha de vencimiento: " + boardInstance.RESET + filtered.GetDueDate());
                            System.out.println(boardInstance.YELLOW + "----------------------------------------------------------------------------------------------------" + boardInstance.RESET);
                            System.out.println(boardInstance.CYAN + "Menu de tarea" + boardInstance.RESET);
                            System.out.println("1. Modificar contenido");
                            System.out.println("2. Modificar Vencimiento");
                            System.out.println(boardInstance.RED + "3. Eliminar tarea" + boardInstance.RESET);
                            System.out.println(boardInstance.RED + "4. Salir" + boardInstance.RESET);
                            System.out.println(boardInstance.YELLOW + "----------------------------------------------------------------------------------------------------" + boardInstance.RESET);

                            System.out.println(boardInstance.CYAN + "Ingrese la opcion: " + boardInstance.RESET);
                            Scanner inputSubOpc = new Scanner(System.in);
                            subOpc = inputSubOpc.nextInt();

                            switch (subOpc) {
                                case 1:
                                    System.out.println(boardInstance.YELLOW + "\n----------------------------------------------------------------------------------------------------" + boardInstance.RESET);
                                    System.out.println(boardInstance.CYAN + "Ingrese el nuevo contenido: " + boardInstance.RESET);
                                    Scanner inputBody = new Scanner(System.in);
                                    String moreBody = inputBody.nextLine();
                                    System.out.println(boardInstance.YELLOW + "----------------------------------------------------------------------------------------------------" + boardInstance.RESET);

                                    filtered.SetBody(moreBody);

                                    System.out.println(boardInstance.GREEN + "\nModificacion exitosa!" + boardInstance.RESET);
                                    System.out.println(boardInstance.YELLOW + "----------------------------------------------------------------------------------------------------" + boardInstance.RESET);
                                    break;

                                case 2: 
                                    System.out.println(boardInstance.YELLOW + "\n----------------------------------------------------------------------------------------------------" + boardInstance.RESET);
                                    System.out.println(boardInstance.CYAN + "Ingrese nueva fecha de vencimiento: " + boardInstance.RESET);
                                    Scanner inputDuedate = new Scanner(System.in);
                                    String moreDueDate = inputDuedate.next();
                                    System.out.println(boardInstance.YELLOW + "----------------------------------------------------------------------------------------------------" + boardInstance.RESET);

                                    filtered.SetDueDate(moreDueDate);

                                    System.out.println(boardInstance.GREEN + "Modificacion exitosa!" + boardInstance.RESET);
                                    System.out.println(boardInstance.YELLOW + "----------------------------------------------------------------------------------------------------" + boardInstance.RESET);
                                    break;
                                case 3:
                                    System.out.println(boardInstance.YELLOW + "\n----------------------------------------------------------------------------------------------------" + boardInstance.RESET);
                                    Animation.PrintMsgWithProgressBar((boardInstance.RED + "Eliminando tarea" + boardInstance.RESET) , 85, 8);
                                    System.out.println(boardInstance.YELLOW + "----------------------------------------------------------------------------------------------------" + boardInstance.RESET);
                                    boolean response = boardInstance.RemoveTask(accessID);
                                    if(response){
                                        System.out.println(boardInstance.GREEN + "Eliminacion exitosa!" + boardInstance.RESET);
                                    }else{
                                        System.out.println(boardInstance.RED + "Eliminacion fallida!" + boardInstance.RESET);
                                    }
                                    System.out.println(boardInstance.YELLOW + "----------------------------------------------------------------------------------------------------" + boardInstance.RESET);
                                    break;
                                case 4: 
                                    break;
                                default:
                                    System.out.println(boardInstance.RED + "\n\nOpcion invalida => intente de nuevo!\n\n" + boardInstance.RESET);
                                    break;
                            }

                        }while(subOpc != 4 && subOpc != 3);
                    }else{
                        System.out.println(boardInstance.RED + "\n\nID ingresado es invalido => intente de nuevo!\n\n" + boardInstance.RESET);
                    };
                    break;

                case 3:
                //does nothing
                    break;

                default:
                    System.out.println(boardInstance.RED + "\n\nOpcion invalida => intente de nuevo!\n\n" + boardInstance.RESET);
                    break;
            }
        }while(opc != 3);
        
    }
}
