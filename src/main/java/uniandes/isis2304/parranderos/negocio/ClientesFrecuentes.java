package uniandes.isis2304.parranderos.negocio;

public class ClientesFrecuentes {
	
	private long idcliente;
	private long veces;
	
	public ClientesFrecuentes() {
		
		this.idcliente = 0;
		this.veces = 0;
	}
	
	public ClientesFrecuentes(long idcliente, long veces) {
		
		this.idcliente = idcliente;
		this.veces = veces;
	}

	public long getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(long idcliente) {
		this.idcliente = idcliente;
	}

	public long getVeces() {
		return veces;
	}

	public void setVeces(long veces) {
		this.veces = veces;
	}

	@Override
	public String toString() {
		return "ClientesFrecuentes [idcliente=" + idcliente + ", veces=" + veces + "]";
	}
	
	
	
	

}
