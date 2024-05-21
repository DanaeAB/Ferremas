import axiosInstance from "../axiosConfig.js";

class CategoriasService {
  static BASE_URL = "categorias";

  static obtenerCategorias() {
    return axiosInstance.get(this.BASE_URL);
  }
}

export default CategoriasService;