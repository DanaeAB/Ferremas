function formatearPrecio(precio) {
  const clp = new Intl.NumberFormat('es-CL', {
    style: 'currency',
    currency: 'CLP',
  });

  return clp.format(precio);
}