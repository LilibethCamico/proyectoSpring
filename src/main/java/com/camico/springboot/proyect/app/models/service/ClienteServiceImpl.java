package com.camico.springboot.proyect.app.models.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.camico.springboot.proyect.app.models.dao.ICliente;
import com.camico.springboot.proyect.app.models.dao.IFactura;
import com.camico.springboot.proyect.app.models.dao.IProducto;
import com.camico.springboot.proyect.app.models.entity.Cliente;
import com.camico.springboot.proyect.app.models.entity.Factura;
import com.camico.springboot.proyect.app.models.entity.Producto;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private ICliente clienteDao;
	
	@Autowired
	private IProducto productoDao;
	
	@Autowired
	private IFactura facturaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return clienteDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Cliente fethByIdWithFacturas(Long id) {
		return clienteDao.fetchByIdWithFacturas(id);
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByNombre(String term) {
		//return productoDao.findByNombre(term);
		return productoDao.findByNombreLikeIgnoreCase("%" + term + "%");
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findProductoById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delecteFactura(Long id) {
		facturaDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	//para optimizar la busqueda, para que vegan todas las relaciones de una sola vez y no por separado.
	public Factura fetchFacturaByIdWithClienteWithItemFacturaWithProducto(Long id) {
		return facturaDao.fetchByIdWithClienteWithItemFacturaWithProducto(id);
	}

	

}
