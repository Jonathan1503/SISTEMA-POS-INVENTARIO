package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;
import java.sql.Timestamp;


public class Pedido implements VOPedido{
	
	private long idpedido;
	private long idproveedor;
	private long idproducto;
	private double volumen;
	private double preciosubtotal;
	private long sucursal;
	
	public Pedido() {
		
		this.idpedido = 0;
		this.idproveedor = 0;
		this.idproducto = 0;
		this.volumen = 0;
		this.preciosubtotal = 0;
		this.sucursal=0;
	}

	public Pedido(long idpedido, long idproveedor, long idproducto, double volumen, double preciosubtotal,
			long sucursal) {
		
		this.idpedido = idpedido;
		this.idproveedor = idproveedor;
		this.idproducto = idproducto;
		this.volumen = volumen;
		this.preciosubtotal = preciosubtotal;
		this.sucursal = sucursal;
	}

	public long getIdpedido() {
		return idpedido;
	}

	public void setIdpedido(long idpedido) {
		this.idpedido = idpedido;
	}

	public long getIdproveedor() {
		return idproveedor;
	}

	public void setIdproveedor(long idproveedor) {
		this.idproveedor = idproveedor;
	}

	public long getIdproducto() {
		return idproducto;
	}

	public void setIdproducto(long idproducto) {
		this.idproducto = idproducto;
	}

	public double getVolumen() {
		return volumen;
	}

	public void setVolumen(double volumen) {
		this.volumen = volumen;
	}

	public double getPreciosubtotal() {
		return preciosubtotal;
	}

	public void setPreciosubtotal(double preciosubtotal) {
		this.preciosubtotal = preciosubtotal;
	}

	public long getSucursal() {
		return sucursal;
	}

	public void setSucursal(long sucursal) {
		this.sucursal = sucursal;
	}

	@Override
	public String toString() {
		return "Pedido [idpedido=" + idpedido + ", idproveedor=" + idproveedor + ", idproducto=" + idproducto
				+ ", volumen=" + volumen + ", preciosubtotal=" + preciosubtotal + ", sucursal=" + sucursal + "]";
	}
	
	
	
	
	
}
	
	