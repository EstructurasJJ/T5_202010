package model.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
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
import model.data_structures.Node;
import model.data_structures.TablaHashSondeoLineal;


public class Modelo 
{
	private String parteDelComparendo; 
	private Comparendo compaAgregar;
	private boolean coordenadas = false;

	private double minLatitud = 1000000000;
	private double minLongitud = 1000000000;
	private double maxLatitud = -1000000000;
	private double maxLongitud = -1000000000;
	
	//////NUEVO TALLER 5/////
	private TablaHashSondeoLineal<String, Comparendo> HSLBobi;
	/////////////////////////

	public Modelo()
	{
		parteDelComparendo = "";
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

	public void leerGeoJson(String pRuta, int capIni) 
	{	
		//////NUEVO TALLER 5//////
		HSLBobi = new TablaHashSondeoLineal<String, Comparendo>(capIni);
		//////////////////////////
		
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
			
			/////NUEVO TALLER 5/////
	
			String key = getFechaMod(compaAgregar.darFecha_Hora());			
			key = key + "-" + compaAgregar.darClase_Vehi() + "-" + compaAgregar.darInfraccion();
			
			HSLBobi.putInSet(key, compaAgregar);
			
			key = "";
			////////////////////////
			
			compaAgregar = null;

			//System.out.println("///AGREGADO///");

		}
	}
	
	public String getFechaMod(Date fechaMod)
	{
	    SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
	    return sf.format(fechaMod);
	}


	//TODO TALLER 5 NUEVO

	public TablaHashSondeoLineal<String, Comparendo> darHashLineal()
	{
		return HSLBobi;
	}

	public void busquedaLinealHash(String fecha, String claseV, String infrac)
	{
		String key= fecha + "-" + claseV + "-" + infrac;
		
		Comparable[] tablita = copiarComparendos(key);
		ordenamientoPorQuickSort(tablita);
		
		for(int i = 0; i < tablita.length; i++)
		{
			Comparendo compi =  (Comparendo) tablita[i];
			
			System.out.println("---------------------");
			System.out.println("ObjectID: " + compi.darObjectid());
			System.out.println("Fecha Hora: " + compi.darFecha_Hora().toString());
			System.out.println("Infracción: " + compi.darInfraccion());
			System.out.println("Clase Vehiculo: " + compi.darClase_Vehi());
			System.out.println("Localidad: " + compi.darLocalidad());
		}
		 
	}
	
	public ArrayList<Long> analisisTablaLineal()
	{
		ArrayList<Long> listaTiempos = new ArrayList<Long>();
		
		int llaveExiste= 0;
		int llaveNOExiste = 0;
		
		Iterator<String> iter = HSLBobi.keys();
		
		while(iter.hasNext() && llaveExiste <= 8000)
		{
			long tiempo= System.currentTimeMillis();
			HSLBobi.getSet(iter.next());
			long tiemp=System.currentTimeMillis()-tiempo;
			
			listaTiempos.add(tiemp);
			llaveExiste++;
		}		
		while(llaveNOExiste <= 2000)
		{
			int dos = (int) (Math.random() *999);
			String llaves = "" + dos;
			
			long tiempo= System.currentTimeMillis();
			HSLBobi.getSet(llaves);
			long tiemp=System.currentTimeMillis()-tiempo;
			
			listaTiempos.add(tiemp);
			llaveNOExiste++;
		}
		return listaTiempos;
	}
	
	////////////////////////////////////////////////////////////////////////OBTENER COMPARENDOS ORDENADOS/////
	
	public Comparable[] copiarComparendos(String key)
	{		
		Iterator<Comparendo> tablitaTam = HSLBobi.getSet(key);
		Iterator<Comparendo> tablita = HSLBobi.getSet(key);
		
		int tamaño = 0;
		
		while(tablitaTam.hasNext())
		{
			tablitaTam.next();
			tamaño++;
		}
		
		Comparable[] comparendosCopia = new Comparable[tamaño];
		int contador = 0;
		
		while (tablita.hasNext())
		{
			Comparendo compi = tablita.next();
			comparendosCopia[contador] = compi;
			contador++;
		}
		
		return comparendosCopia;
	}
	
	

	/////////////////////////////////////////////////////////////////////////////////ORDENAMIENTO//////
	
	//Quick Sort
	
	public static void ordenamientoPorQuickSort(Comparable [] a)
	{
		//Volverlo aleatorio
		
		Random rnd = ThreadLocalRandom.current();

		for (int i = a.length - 1; i > 0; i--)
		{
			int index = rnd.nextInt(i + 1);
			Comparable x = a[index];
			a[index] = a[i];
			a[i] = x;
		}
		
		//Ordenarlo
		
		quickSort(a, 0, a.length-1);
		
	}
	
	//Ordenar las subpartes.
	
	private static void  quickSort(Comparable[] a, int lo, int hi)
	{
		if (hi<=lo) return;
		
		int j = partition (a,lo,hi);
		quickSort(a,lo,j-1);
		quickSort(a,j+1,hi);
	}
	
	//Donde se parte el arreglo
	
	private static int partition(Comparable[] a, int lo, int hi)
	{
		int i=lo, j=hi+1; //Indices derecha e izquierda
		Comparable v=a[lo];
		
		while (true)
		{
			while (a[++i].compareTo(v)<0) if (i==hi) break;
			while (v.compareTo(a[--j])<0) if (j==lo) break;
			if (i>=j) break;
				exchange(a,i,j);
		}
		
		exchange(a,lo,j);
		return j;
	}
	
	//Intercambio
	
	public static void exchange(Comparable[] copia, int pos1, int pos2)
	{
		Comparable tempo = copia[pos1];
		copia[pos1] = copia[pos2];
		copia[pos2] = tempo;
	}
	

}






