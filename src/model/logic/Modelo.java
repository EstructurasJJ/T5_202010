package model.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

import model.data_structures.ListaEnlazadaQueue;
import model.data_structures.MaxColaCP;
import model.data_structures.MaxHeapCP;
import model.data_structures.Node;


public class Modelo 
{
	private String parteDelComparendo; 
	private Comparendo compaAgregar;
	private boolean coordenadas = false;

	private double minLatitud = 1000000000;
	private double minLongitud = 1000000000;
	private double maxLatitud = -1000000000;
	private double maxLongitud = -1000000000;

	private MaxHeapCP<Comparendo> datosHeap;
	private MaxColaCP<Comparendo> datosCola;
	private ListaEnlazadaQueue<Comparendo> booty = new ListaEnlazadaQueue<Comparendo>();

	public Modelo()
	{
		parteDelComparendo = "";
		datosHeap = new MaxHeapCP<Comparendo>();
		booty = new ListaEnlazadaQueue<Comparendo>();
	}


	public MaxHeapCP<Comparendo> darDatos()
	{
		return datosHeap;
	}

	public MaxColaCP<Comparendo> darMaxCola()
	{
		return datosCola;
	}

	public int darTamanio()
	{
		return booty.darTamanio();
	}

	public Comparendo PrimerComparendo()
	{
		return booty.darPrimerElemento().darInfoDelComparendo();
	}

	public Comparendo UltimoComparendo()
	{
		return booty.darUltimoElemento().darInfoDelComparendo();
	}

	public double darMinLatitud()
	{
		return minLatitud;
	}
	public double darMinLongitud()
	{
		return minLongitud;
	}
	public double darMaxLatitud()
	{
		return maxLatitud;
	}
	public double darMaxLongitud()
	{
		return maxLongitud;
	}

	public void leerGeoJson(String pRuta) 
	{	
		JsonParser parser = new JsonParser();
		FileReader fr = null;

		try 
		{
			fr = new FileReader(pRuta);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		JsonElement datos = parser.parse(fr);
		dumpJSONElement(datos);

	}

	private void dumpJSONElement(JsonElement elemento) 
	{


		if (elemento.isJsonObject()) 
		{

			JsonObject obj = elemento.getAsJsonObject();

			java.util.Set<java.util.Map.Entry<String,JsonElement>> entradas = obj.entrySet();
			java.util.Iterator<java.util.Map.Entry<String,JsonElement>> iter = entradas.iterator();

			while (iter.hasNext()) 
			{
				java.util.Map.Entry<String,JsonElement> entrada = iter.next();
				componentesDelComparendo(entrada.getKey());	            

				dumpJSONElement(entrada.getValue());
			}

		}
		else if (elemento.isJsonArray()) 
		{			
			JsonArray array = elemento.getAsJsonArray();
			java.util.Iterator<JsonElement> iter = array.iterator();

			while (iter.hasNext()) 
			{
				JsonElement entrada = iter.next();
				dumpJSONElement(entrada);
			}

		} 
		else if (elemento.isJsonPrimitive()) 
		{
			JsonPrimitive valor = elemento.getAsJsonPrimitive();

			if(compaAgregar == null)
			{
				compaAgregar = new Comparendo();
			}

			if(parteDelComparendo.equals("OBJECTID"))
			{
				compaAgregar.asignarObjectid(valor.getAsInt());
				//System.out.println(valor);
				parteDelComparendo = "";
			}
			else if (parteDelComparendo.equals("FECHA_HORA"))
			{
				compaAgregar.asignarFecha_Hora(valor.getAsString());
				//System.out.println(compaAgregar.darFecha_Hora().toString());
				parteDelComparendo = "";
			}
			else if (parteDelComparendo.equals("MEDIO_DETECCION"))
			{
				compaAgregar.asignarMedio_Dete(valor.getAsString());
				//System.out.println(valor);
				parteDelComparendo = "";
			}
			else if (parteDelComparendo.equals("CLASE_VEHICULO"))
			{
				compaAgregar.asignarClase_Vehi(valor.getAsString());
				//System.out.println(valor);
				parteDelComparendo = "";
			}
			else if (parteDelComparendo.equals("TIPO_SERVICIO"))
			{
				compaAgregar.asignarTipo_Servicio(valor.getAsString());
				//System.out.println(valor);
				parteDelComparendo = "";
			}
			else if (parteDelComparendo.equals("INFRACCION"))
			{
				compaAgregar.asignarInfraccion(valor.getAsString());
				//System.out.println(valor);
				parteDelComparendo = "";
			}
			else if (parteDelComparendo.equals("DES_INFRACCION"))
			{
				compaAgregar.asignarDes_Infrac(valor.getAsString());
				//System.out.println(valor);
				parteDelComparendo = "";

			}
			else if (parteDelComparendo.equals("LOCALIDAD"))
			{				
				compaAgregar.asignarLocalidad(valor.getAsString());
				//System.out.println(valor);	
				parteDelComparendo = "";
			}
			else if (parteDelComparendo.equals("MUNICIPIO"))
			{				
				compaAgregar.asignarMunicipio(valor.getAsString());
				//System.out.println(valor);	
				parteDelComparendo = "";
			}
			else if (parteDelComparendo.equals("coordinates"))
			{
				agregarCoordenada(valor.getAsDouble());				
			}

		} 
		else if (elemento.isJsonNull()) 
		{
			System.out.println("Es NULL");
		} 
		else 
		{
			System.out.println("Es otra cosa");
		}

	}

	public void componentesDelComparendo(String palabra)
	{
		if (palabra.equals("OBJECTID"))
		{
			parteDelComparendo = "OBJECTID";
		}
		else if (palabra.equals("FECHA_HORA"))
		{
			parteDelComparendo = "FECHA_HORA";
		}
		else if (palabra.equals("MEDIO_DETECCION"))
		{
			parteDelComparendo = "MEDIO_DETECCION";
		}
		else if (palabra.equals("CLASE_VEHICULO"))
		{
			parteDelComparendo = "CLASE_VEHICULO";
		}
		else if (palabra.equals("TIPO_SERVICIO"))
		{
			parteDelComparendo = "TIPO_SERVICIO";
		}
		else if (palabra.equals("INFRACCION"))
		{
			parteDelComparendo = "INFRACCION";
		}
		else if (palabra.equals("DES_INFRACCION"))
		{
			parteDelComparendo = "DES_INFRACCION";
		}
		else if (palabra.equals("LOCALIDAD"))
		{
			parteDelComparendo = "LOCALIDAD";
		}
		else if (palabra.equals("MUNICIPIO"))
		{
			parteDelComparendo = "MUNICIPIO";
		}
		else if (palabra.equals("coordinates"))
		{
			parteDelComparendo = "coordinates";
		}
	}

	public void agregarCoordenada(double pCor)
	{
		if(coordenadas == false)
		{
			compaAgregar.asignarLongitud(pCor);
			//System.out.println("Longitud: " + pCor);

			if (pCor < minLongitud)
			{
				minLongitud = pCor;
			}
			else if (pCor > maxLongitud)
			{
				maxLongitud = pCor;
			}

			coordenadas = true;
		}

		else
		{
			compaAgregar.asignarLatitud(pCor);
			//System.out.println("Latitud: " + pCor);

			if (pCor < minLatitud)
			{
				minLatitud = pCor;
			}
			else if (pCor > maxLatitud)
			{
				maxLatitud = pCor;
			}

			//AGREGAR//

			coordenadas = false;
			parteDelComparendo = "";

			booty.enqueue(compaAgregar);
			compaAgregar = null;

			//System.out.println("///AGREGADO///");

		}
	}


	//TODO TALLER 4 NUEVO

	public Comparendo[] copiarComparendos()
	{
		Comparendo[] comparendosCopia = new Comparendo[booty.darTamanio()+1];
		int contador = 1;

		Node<Comparendo> actual = booty.darPrimerElemento();

		while(actual != null)
		{
			Comparendo compi = actual.darInfoDelComparendo();
			comparendosCopia[contador] = compi;

			contador++;
			actual = actual.darSiguiente();

		}

		return comparendosCopia;
	}

	public MaxHeapCP<Comparendo> generarMuestraHeap(int N)
	{
		MaxHeapCP<Comparendo> heapProv = new MaxHeapCP<Comparendo>();

		Comparable[] arr = copiarComparendos();

		for(int i=1; i<=N; i++)
		{
			int random = (int) Math.floor((booty.darTamanio() * Math.random()));
			heapProv.añadir((Comparendo) arr[random+1]);
		}

		return heapProv;
	}

	public MaxColaCP<Comparendo> generarMuestraCola(int N)
	{
		MaxColaCP<Comparendo> colaProv = new MaxColaCP<Comparendo>();

		Comparable[] arr = copiarComparendos();

		for(int i=1; i<=N; i++)
		{
			int random = (int) Math.floor((booty.darTamanio() * Math.random()));
			colaProv.agregar((Comparendo) arr[random]);
		}

		return colaProv;
	}



	public MaxHeapCP<Comparendo> norteMaxHeapCP(int N, String[] listica)
	{
		MaxHeapCP<Comparendo> heapProv = new MaxHeapCP<Comparendo>();

		long tiempoComienzo=System.currentTimeMillis();
		MaxHeapCP<Comparendo> heapAleat = generarMuestraHeap(N);
		long tiempo= System.currentTimeMillis()-tiempoComienzo;
		System.out.println("El tiempo en generar la muestra aleatoria fue: " + tiempo + " milisegundos." + "\n----------");

		int cantidad = listica.length;

		Comparable[] arr = heapAleat.darHeap();

		for(int i = 0; i <= cantidad-1; i++)
		{
			for(int j = 1; j <= heapAleat.darTamaño(); j++)
			{
				if (((Comparendo) arr[j]).darClase_Vehi().equals(listica[i]))
				{
					Comparendo compi = (Comparendo) arr[j];
					heapProv.añadir(compi);
				}
			}
		}

		return heapProv;
	}


	public MaxColaCP<Comparendo> norteMaxColaCP(int N, String[] listica)
	{
		MaxColaCP<Comparendo> colaProv = new MaxColaCP<Comparendo>();

		long tiempoComienzo=System.currentTimeMillis();
		MaxColaCP<Comparendo> colaAleat = generarMuestraCola(N);
		long tiempo= System.currentTimeMillis()-tiempoComienzo;
		System.out.println("El tiempo en generar la muestra aleatoria fue: " + tiempo + " milisegundos." + "\n----------");

		Comparable[] a=colaAleat.darDatosEnArreglo();
		int c=0;
		while(c<a.length)
		{
			for (int i=0;i<listica.length;i++)
			{
				String aux="";
				if (a[c]!=null)
				{
					aux = ((Comparendo)a[c]).darClase_Vehi();	
				}

				String aux2=listica[i];
				if (aux.equals(listica[i]))
				{
					colaProv.agregar((Comparendo)a[c]);
				}
			}
			c++;
		}


		return colaProv;
	}





}
