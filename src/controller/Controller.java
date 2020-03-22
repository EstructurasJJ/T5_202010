package controller;

import java.util.Scanner;

import model.data_structures.Node;
import model.logic.Comparendo;
import model.logic.Modelo;
import view.View;

public class Controller {

	private Modelo modelo;
	private View view;
	public final static String RUTAGEOJASON = "./data/Comparendos_DEI_2018_Bogotá_D.C.geojson";
	public final static String JUEGUEMOS = "./data/Comparendos_DEI_2018_Bogotá_D.C_small.geojson";

	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
	}

	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String dato = "";
		String respuesta = "";

		Comparendo[] arreglo = null;

		while( !fin )
		{
			view.printMenu();

			int option = lector.nextInt();

			int nHeap;
			String lista;

			switch(option)
			{
			case 1:

				//Cargar el archivo

				modelo.leerGeoJson(JUEGUEMOS);

				view.printMessage("Archivo GeoJSon Cargado");
				view.printMessage("Numero actual de comparendos " + modelo.darTamanio() + "\n----------");

				//Primer Comparendo

				view.printMessage("La información del primer Object Id es: ");
				view.printMessage("Object Id: " + modelo.PrimerComparendo().darObjectid());
				view.printMessage("Fecha Hora: " + modelo.PrimerComparendo().darFecha_Hora().toString());
				view.printMessage("Infracción: " + modelo.PrimerComparendo().darInfraccion());
				view.printMessage("Clase Vehiculo: " + modelo.PrimerComparendo().darClase_Vehi());
				view.printMessage("Tipo Servicio: " + modelo.PrimerComparendo().darTipo_Servicio());
				view.printMessage("Localidad: " + modelo.PrimerComparendo().darLocalidad());
				view.printMessage("Municipio: " + modelo.PrimerComparendo().darMunicipio() + "\n----------");

				//Último Comparendo

				view.printMessage("La información del último Object Id es: ");
				view.printMessage("Object Id: " + modelo.UltimoComparendo().darObjectid());
				view.printMessage("Fecha Hora: " + modelo.UltimoComparendo().darFecha_Hora().toString());
				view.printMessage("Infracción: " + modelo.UltimoComparendo().darInfraccion());
				view.printMessage("Clase Vehiculo: " + modelo.UltimoComparendo().darClase_Vehi());
				view.printMessage("Tipo Servicio: " + modelo.UltimoComparendo().darTipo_Servicio());
				view.printMessage("Localidad: " + modelo.UltimoComparendo().darLocalidad());
				view.printMessage("Municipio: " + modelo.UltimoComparendo().darMunicipio() + "\n----------");


				break;

			case 2:


				break;

			case 3:


				break;

			case 4:

				view.printMessage("--------- \n Hasta pronto !! \n---------"); 
				lector.close();
				fin = true;

				break;	

			default: 

				view.printMessage("--------- \n Opción Invalida !! \n---------");

				break;

			}
		}

	}	
}
