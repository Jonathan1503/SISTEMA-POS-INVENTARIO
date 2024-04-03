package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public interface VOPedidoConsolidado {
	
	public long getIdpedido();
	public long getIdProveedor();
	public double getPrecioTotal() ;
	public  Timestamp getFechaesperada();
	public  Timestamp getFechaentrega() ;
	public double getCalificacion();
	public String getEstado();
	public long getSucursal();
	@Override
	public String toString();

}
