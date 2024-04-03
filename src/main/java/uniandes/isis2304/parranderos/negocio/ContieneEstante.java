package uniandes.isis2304.parranderos.negocio;

public class ContieneEstante {
	
	private long idproducto;
	private long idestante;
	private long cantidad;
	
	public ContieneEstante(long idproducto, long idestante, long cantidad) {
		
		this.idproducto = idproducto;
		this.idestante = idestante;
		this.cantidad = cantidad;
	}
	
	public ContieneEstante() {
		
		this.idproducto = 0;
		this.idestante = 0;
		this.cantidad = 0;
	}

	public long getIdproducto() {
		return idproducto;
	}

	public void setIdproducto(long idproducto) {
		this.idproducto = idproducto;
	}

	public long getIdestante() {
		return idestante;
	}

	public void setIdestante(long idestante) {
		this.idestante = idestante;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "ContieneEstante [idproducto=" + idproducto + ", idestante=" + idestante + ", cantidad=" + cantidad
				+ "]";
	}
	
	
	

}
