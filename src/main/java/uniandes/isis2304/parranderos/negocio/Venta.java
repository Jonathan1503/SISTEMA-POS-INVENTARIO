package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;
import java.sql.Timestamp;

public class Venta implements VOVenta {
	
	private long id;
	private long idCliente;
	private long idProducto;
	private long cantidad;
	private  Timestamp fechaVenta;
	private double total;
	private long sucursal;
	
	public Venta() {
		super();
		this.id = 0;
		this.idCliente = 0;
		this.idProducto = 0;
		this.cantidad = 0;
		this.fechaVenta = new  Timestamp(0);
		this.total = 0;
		this.sucursal = 0;
	}
	
	public Venta(long id, long idCliente, long idProducto, long cantidad,  Timestamp fechaVenta, double total,
			long sucursal) {
		super();
		this.id = id;
		this.idCliente = idCliente;
		this.idProducto = idProducto;
		this.cantidad = cantidad;
		this.fechaVenta = fechaVenta;
		this.total = total;
		this.sucursal = sucursal;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	public long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	public  Timestamp getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta( Timestamp fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public long getSucursal() {
		return sucursal;
	}

	public void setSucursal(long sucursal) {
		this.sucursal = sucursal;
	}
	
	public String toString() {
		return"Venta [id="+id+", idCliente="+idCliente+", idProducto="+idProducto+", cantidad="+cantidad+", fechaVenta="+
	fechaVenta+", total="+total+", sucursal="+sucursal+"]";
	}
	
	

}
