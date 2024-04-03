package uniandes.isis2304.parranderos.negocio;

public class Carrito implements VOCarrito{
	
	
	private long idcliente;
	private long id;
	
	public Carrito(){
	
		this.idcliente=0;
		this.id=0;
	}

	public Carrito(long id, long idcliente) {
		
		
		this.idcliente = idcliente;
		this.id = id;
	}



	public long getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(long idcliente) {
		this.idcliente = idcliente;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Carrito [idcliente=" + idcliente + ", id=" + id + "]";
	}

	
	

	

}
