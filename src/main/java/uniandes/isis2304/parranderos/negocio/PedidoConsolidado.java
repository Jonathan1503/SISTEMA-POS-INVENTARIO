package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class PedidoConsolidado {
	
	private long idpedido;
	private long idproveedor;
	private double preciototal;
	private Timestamp fechaesperada;
	private Timestamp fechaentrega; 
	private double calificacion; 
	private String estado;
	private long sucursal;
	
	public PedidoConsolidado() {
		
		this.idpedido = 0;
		this.idproveedor = 0;
		this.preciototal = 0;
		this.fechaesperada = new  Timestamp(0);
		this.fechaentrega = new  Timestamp(0);
		this.calificacion = 0;
		this.estado = "";
		this.sucursal = 0;
	}
	
	public PedidoConsolidado(long idpedido, long idproveedor, double preciototal, Timestamp fechaesperada,
			Timestamp fechaentrega, double calificacion, String estado, long sucursal) {
		
		this.idpedido = idpedido;
		this.idproveedor = idproveedor;
		this.preciototal = preciototal;
		this.fechaesperada = fechaesperada;
		this.fechaentrega = fechaentrega;
		this.calificacion = calificacion;
		this.estado = estado;
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

	public double getPreciototal() {
		return preciototal;
	}

	public void setPreciototal(double preciototal) {
		this.preciototal = preciototal;
	}

	public Timestamp getFechaesperada() {
		return fechaesperada;
	}

	public void setFechaesperada(Timestamp fechaesperada) {
		this.fechaesperada = fechaesperada;
	}

	public Timestamp getFechaentrega() {
		return fechaentrega;
	}

	public void setFechaentrega(Timestamp fechaentrega) {
		this.fechaentrega = fechaentrega;
	}

	public double getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(double calificacion) {
		this.calificacion = calificacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public long getSucursal() {
		return sucursal;
	}

	public void setSucursal(long sucursal) {
		this.sucursal = sucursal;
	}

	@Override
	public String toString() {
		return "PedidoConsolidado [idpedido=" + idpedido + ", idproveedor=" + idproveedor + ", preciototal="
				+ preciototal + ", fechaesperada=" + fechaesperada + ", fechaentrega=" + fechaentrega
				+ ", calificacion=" + calificacion + ", estado=" + estado + ", sucursal=" + sucursal + "]";
	}
	
	
	
	

}
