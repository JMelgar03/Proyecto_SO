package model;

public class Proceso implements Comparable<Proceso>{
		private int id;
		private int estado;
		private int prioridad;
		private int cantidadInstrucciones;
		private int instruccionBloqueo;
		private int evento;
		private int instruccionesEjecutadas;
		private int instruccionesEsperaBloqueo;
		
		public int getInstruccionesEsperaBloqueo(){
			return instruccionesEsperaBloqueo;
		}

		public void setInstruccionesEsperaBloqueo(int instruccionesEsperaBloqueo) {
			this.instruccionesEsperaBloqueo = instruccionesEsperaBloqueo;
		}

		public int getContadorInstruccionesBloqueadas() {
			return contadorInstruccionesBloqueadas;
		}

		public void setContadorInstruccionesBloqueadas(
				int contadorInstruccionesBloqueadas) {
			this.contadorInstruccionesBloqueadas = contadorInstruccionesBloqueadas;
		}

		private int contadorInstruccionesBloqueadas;
		
		public Proceso(int id, int estado, int prioridad,
				int cantidadInstrucciones, int instruccionBloqueo, int evento){
			this.id = id;
			this.estado = estado;
			this.prioridad = prioridad;
			this.cantidadInstrucciones = cantidadInstrucciones;
			this.instruccionBloqueo = instruccionBloqueo;
			this.evento = evento;
			this.instruccionesEjecutadas = 0;
			this.contadorInstruccionesBloqueadas = 0;
			
			if(evento == 3)
				this.instruccionesEsperaBloqueo = 13;
			
			else if(evento == 5)
				this.instruccionesEsperaBloqueo = 27;
		}

		public int getId(){
			return id;
		}
	
		public void setId(int id){
			this.id = id;
		}
	
		public int getEstado(){
			return estado;
		}
	
		public void setEstado(int estado){
			this.estado = estado;
		}
	
		public int getCantidadInstrucciones(){	
			return cantidadInstrucciones;
		}
	
		public void setCantidadInstrucciones(int cantidadInstrucciones){
			this.cantidadInstrucciones = cantidadInstrucciones;
		}
	
		public int getInstruccionBloqueo(){
			return instruccionBloqueo;
		}
	
		public void setInstruccionBloqueo(int instruccionBloqueo){
			this.instruccionBloqueo = instruccionBloqueo;
		}
	
		public int getEvento(){
			return evento;
		}
	
		public void setEvento(int evento){
			this.evento = evento;
		}
	
		public int getInstruccionesEjecutadas(){
			return instruccionesEjecutadas;
		}
	
		public void setInstruccionesEjecutadas(int instruccionesRestantes){
			this.instruccionesEjecutadas = instruccionesRestantes;
		}
	
		public int getPrioridad(){
			return prioridad;
		}
	
		public void setPrioridad(int prioridad){
			this.prioridad = prioridad;
		}
		
		@Override
		public int compareTo(Proceso proceso){
			int prioridadComparar = (proceso.getPrioridad());
				
			return this.prioridad - prioridadComparar;
		}
	
		@Override
		public String toString(){
				return id+"/"+estado+"/"+prioridad+"/"+cantidadInstrucciones+"/"+instruccionBloqueo+"/"+evento+"/PC:"+instruccionesEjecutadas;
		}
		
}
