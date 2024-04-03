package uniandes.isis2304.parranderos.negocio;

public class resulIndice {
	
	long idbodega;
	double indiceocupacion;
	public resulIndice(long idbodega, double indiceocupacion) {
		
		this.idbodega = idbodega;
		this.indiceocupacion = indiceocupacion;
	}
	
	public resulIndice() {


		this.idbodega = 0;
		this.indiceocupacion = 0;
	}

	public long getIdbodega() {
		return idbodega;
	}

	public void setIdbodega(long idbodega) {
		this.idbodega = idbodega;
	}

	public double getIndiceocupacion() {
		return indiceocupacion;
	}

	public void setIndiceocupacion(double indiceocupacion) {
		this.indiceocupacion = indiceocupacion;
	}
	
	
	

}
