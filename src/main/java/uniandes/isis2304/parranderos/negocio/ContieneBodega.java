package uniandes.isis2304.parranderos.negocio;

public class ContieneBodega {
	
	private long idproducto;
	private long idbodega;
	private long cantidad;
	
	public ContieneBodega(long idproducto, long idbodega, long cantidad) {
		
		this.idproducto = idproducto;
		this.idbodega = idbodega;
		this.cantidad = cantidad;
	}
	
	public ContieneBodega() {
		
		this.idproducto = 0;
		this.idbodega = 0;
		this.cantidad = 0;
	}

	public long getIdproducto() {
		return idproducto;
	}

	public void setIdproducto(long idproducto) {
		this.idproducto = idproducto;
	}

	public long getIdbodega() {
		return idbodega;
	}

	public void setIdbodega(long idbodega) {
		this.idbodega = idbodega;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "ContieneBodega [idproducto=" + idproducto + ", idbodega=" + idbodega + ", cantidad=" + cantidad + "]";
	}
	
	
	
	

}
