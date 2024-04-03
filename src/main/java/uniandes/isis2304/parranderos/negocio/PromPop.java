package uniandes.isis2304.parranderos.negocio;

public class PromPop {
	
	long idpromocion;
	long vendido;
	public PromPop() {
		super();
		this.idpromocion = 0;
		this.vendido = 0;
	}
	public PromPop(long idpromocion, long vendido) {
		super();
		this.idpromocion = idpromocion;
		this.vendido = vendido;
	}
	public long getIdpromocion() {
		return idpromocion;
	}
	public void setIdpromocion(long idpromocion) {
		this.idpromocion = idpromocion;
	}
	public long getVendido() {
		return vendido;
	}
	public void setVendido(long vendido) {
		this.vendido = vendido;
	}
	
	

}
