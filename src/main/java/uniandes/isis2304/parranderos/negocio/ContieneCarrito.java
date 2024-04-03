package uniandes.isis2304.parranderos.negocio;

public class ContieneCarrito {
	
	private long idcarrito;
	private long idproducto;
	private long cantidad;
	
	
	public ContieneCarrito(long idcarrito, long idproducto, long cantidad) {
		this.idcarrito = idcarrito;
		this.idproducto = idproducto;
		this.cantidad = cantidad;
	}
	
	public ContieneCarrito() {
		this.idcarrito = 0;
		this.idproducto = 0;
		this.cantidad = 0;
	}
	
	public long getIdcarrito() {
		return idcarrito;
	}
	public void setIdcarrito(long idcarrito) {
		this.idcarrito = idcarrito;
	}
	public long getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(long idproducto) {
		this.idproducto = idproducto;
	}
	public long getCantidad() {
		return cantidad;
	}
	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "ContieneCarrito [idcarrito=" + idcarrito + ", idproducto=" + idproducto + ", cantidad=" + cantidad
				+ "]";
	}
	
	
	
	

}
