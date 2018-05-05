package application;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Proceso;

import org.apache.commons.io.IOUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SampleController implements Initializable {

	File archivoTexto;

	String[] textoEnArreglo;
	String[] arregloAuxiliar;
	
	char[] arregloCaracteres;
	
	String textoEnCadena;
	String errores;
	
	int cantidadTotalCiclos;
	int idUltimoProceso;
	int contadorUltimoProceso;
	
	@FXML TextField txtCiclos;
	@FXML ObservableList<String> listaNuevo;
	@FXML ObservableList<Proceso> listaListo;
	@FXML ObservableList<Proceso> listaEjecucion;
	@FXML ObservableList<Proceso> listaBloqueado;
	@FXML ObservableList<Proceso> listaTerminado;
	
	@FXML ListView<String> lvNuevo;
	@FXML ListView<Proceso> lvListo;
	@FXML ListView<Proceso> lvEjecucion;
	@FXML ListView<Proceso> lvBloqueado;
	@FXML ListView<Proceso> lvTerminado;
	
	Pattern patron;
	Matcher matcher;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		idUltimoProceso = 0;
		contadorUltimoProceso = 0;
		
		listaNuevo = FXCollections.observableArrayList();
		listaListo = FXCollections.observableArrayList();
		listaEjecucion = FXCollections.observableArrayList();
		listaBloqueado = FXCollections.observableArrayList();
		listaTerminado = FXCollections.observableArrayList();
		
		patron = Pattern.compile("[0-9]{4}/[0-9]{1}/[0-9]{1}/[0-9]{3}/[0-9]{3}/[0-9]{1}");
				
		lvNuevo.setItems(listaNuevo);
		lvListo.setItems(listaListo);
		lvEjecucion.setItems(listaEjecucion);
		lvBloqueado.setItems(listaBloqueado);
		lvTerminado.setItems(listaTerminado);
		
		cargarArchivo();
                System.out.println(listaListoToString());
		
		
	}
	
	@FXML public void cargarArchivo(){
		archivoTexto = seleccionarArchivo(); 
		
		try {
			convertirTextoCadena(archivoTexto);
		}catch (Exception e) 
		{
			
		}
		
		dividirCadena();		
		inicializarListo();		
		Collections.sort(listaListo);
		
	}
	
	public void dividirCadena(){
		 textoEnArreglo = textoEnCadena.split(";");
	     
		 for (int i=0; i < textoEnArreglo.length; i++){
			 listaNuevo.add(textoEnArreglo[i]);
		 }
		 
	}
	
	public void inicializarListo(){
		String todosErrores = "";
		
		for (int i=0; i < textoEnArreglo.length; i++){
			errores = "";
			
			arregloCaracteres = textoEnArreglo[i].toCharArray();
			
			matcher = patron.matcher(textoEnArreglo[i]);
			
			//Validar longitud del arreglo
			
			if((textoEnArreglo[i].length() != 18)){
				 System.out.println( "El proceso " + i + " fue eliminado por tamanio no valido\n");
				 errores = errores + "El proceso " + i + " fue eliminado por tamanio no valido\n";
			 }
			 
			 //Validar plecas correspondientes
			 
			 else if ((arregloCaracteres[4] != '/') || (arregloCaracteres[6] != '/') || (arregloCaracteres[8] != '/') ||
					 (arregloCaracteres[12] != '/') || (arregloCaracteres[16] != '/'))
			 {
				 System.out.println("El proceso " + i + "fue eliminado por no tener el numero de plecas adecuadas\n");
				 
				 errores = errores + "El proceso " + i + "fue eliminado por no tener el numero de plecas adecuadas\n";
			 }
			 
			 //Validar prioridad
			 
			 else if((arregloCaracteres[7] != '1') && (arregloCaracteres[7] != '2') && (arregloCaracteres[7] != '3'))
			 {
				 System.out.println( "El proceso "+i+" fue eliminado por prioridad invalida\n");
				 errores = errores + "El proceso "+i+" fue eliminado por prioridad invalida\n";
			 }
			 
			 //Validar evento
			 
			 else if((arregloCaracteres[17] != '3') && (arregloCaracteres[17] != '5'))
			 {
				 System.out.println("El proceso "+i+" fue eliminado por evento invalido\n");
				 errores = errores + "El proceso "+i+" fue eliminado por evento invalido\n" ;
			 }
			 
			 //Validar id del proceso
			 
			 else if(!matcher.matches()){
				 System.out.println("El proceso "+i+" fue eliminado por tener algun otro error\n");
				 errores = errores + " El proceso "+i+" fue eliminado por tener algun otro error\n";
			 }
			 
			 //En caso de que la cadena con errores continue vacia, agrega elemento a Listo
			 
			 if(errores.isEmpty()){
				 arregloAuxiliar = textoEnArreglo[i].split("/");
				 
				 listaListo.add(new Proceso(Integer.parseInt(arregloAuxiliar[0]),
						 1, Integer.parseInt(arregloAuxiliar[2]),
								 Integer.parseInt(arregloAuxiliar[3]), Integer.parseInt(arregloAuxiliar[4]), 
										 Integer.parseInt(arregloAuxiliar[5])));
			 }
			 
			
			 
			 todosErrores = todosErrores + errores;
		 }
		
	
		System.out.println(listaListo);
		
	}
	
	@FXML public void salir(){
		System.exit(0);
	}
	
	
	

	
	public void convertirTextoCadena(File archivoTexto) throws Exception{
		FileInputStream inputStream = new FileInputStream(archivoTexto);
	    try{
	        textoEnCadena = IOUtils.toString(inputStream);
	    }catch(Exception E){
	    	Alert alerta = new Alert(null);
	    	alerta.setContentText("Error al cargar el archivo");
	    	alerta.showAndWait();
	    }
	    
	    finally{
	        inputStream.close();
	    }
	}

	public File seleccionarArchivo(){
		JFileChooser chooser = new JFileChooser();
		
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Solo Archivos .txt", "txt");
	    
	    chooser.setFileFilter(filter);
	    
	    int returnVal = chooser.showOpenDialog(chooser);
	    
	    if(returnVal == JFileChooser.APPROVE_OPTION){
	       return chooser.getSelectedFile();
	    }
	    
	    else return null;
	}
	
	public String listaNuevoToString(){
		String cadena = "";
		
		for(String cadena2 : listaNuevo){
			cadena = cadena + cadena2 + "\n";
		}
		
		return cadena;
	}
	
	public String listaListoToString(){
		String cadena = "\nListo:\n";
		
		for(Proceso proceso : listaListo){
			cadena = cadena + proceso.toString() + "\n";
		}
		
		return cadena;
		
	}
	
	public String listaEjecucionToString(){
		String cadena = "\nEjecucion:\n";
		
		for(Proceso proceso : listaEjecucion)
		{
			cadena = cadena + proceso.toString() + "\n";
		}
		
		return cadena;
		
	}
	
	public String listaBloqueadoToString(){
		String cadena = "\nBloqueado:\n";
		
		for(Proceso proceso : listaBloqueado){
			cadena = cadena + proceso.toString() + "\n";
		}
		
		return cadena;
		
	}
	
	public String listaTerminadoToString(){
		String cadena = "\nTerminado:\n";
		
		for(Proceso proceso : listaTerminado){
			cadena = cadena + proceso.toString() + "\n";
		}
		
		return cadena;	
	}
	
	public void imprimirTablas(){
		System.out.println(listaListoToString());
		System.out.println(listaEjecucionToString());
		System.out.println(listaBloqueadoToString());
		System.out.println(listaTerminadoToString());
		System.out.println("");

	}
	
}
