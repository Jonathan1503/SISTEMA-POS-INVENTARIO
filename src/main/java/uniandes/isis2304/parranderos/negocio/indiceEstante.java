package uniandes.isis2304.parranderos.negocio;

public class indiceEstante {
	
	private long idestante;
	private double indiceocupacion;
	
	public indiceEstante(long idestante, double indiceocupacion) {
		
		this.idestante = idestante;
		this.indiceocupacion = indiceocupacion;
	}
	
	public indiceEstante() {
		
		this.idestante = 0;
		this.indiceocupacion = 0;
	}

	public long getIdestante() {
		return idestante;
	}

	public void setIdestante(long idestante) {
		this.idestante = idestante;
	}

	public double getIndiceocupacion() {
		return indiceocupacion;
	}

	public void setIndiceocupacion(double indiceocupacion) {
		this.indiceocupacion = indiceocupacion;
	}

	@Override
	public String toString() {
		return "indiceEstante [idestante=" + idestante + ", indiceocupacion=" + indiceocupacion + "]";
	}
	
	
	
	
	
	
	

}
