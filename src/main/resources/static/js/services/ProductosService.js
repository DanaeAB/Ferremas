import axiosInstance from "../axiosConfig.js";

class ProductosService {
  static BASE_URL = "productos";

  static obtenerProductos(filtros = {}) {
    let url = `${this.BASE_URL}?`;

    Object.entries(filtros).forEach(([key, value]) => {
      if (value) url += `${key}=${value}&`;
    });

    return axiosInstance.get(url);
  }
  static obtenerProducto(id) {
    return axiosInstance.get(`${this.BASE_URL}/${id}`);
  }
}

export default ProductosService;