package com.controleestoque.api_estoque.Controller;

import com.controleestoque.api_estoque.Model.Categoria;
import com.controleestoque.api_estoque.Repository.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias") // Define o caminho base para este controller
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaRepository categoriaRepository; // Injeção de dependência

    // GET /api/categorias
    @GetMapping
    public List<Categoria> getAllCategorias() {
        // Retorna todos os registros do banco de dados
        return categoriaRepository.findAll();
    }

    // GET /api/categorias/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
        // Busca a categoria pelo ID e retorna 404 se não encontrar.
        return categoriaRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/categorias
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Retorna código 201 (Created)
    public Categoria createCategoria(@RequestBody Categoria categoria) {
        // Recebe a categoria no corpo da requisição e salva no banco de dados
        return categoriaRepository.save(categoria);
    }

    // PUT /api/categorias/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoriaDetails) {
        // Tenta encontrar a categoria existente
        return categoriaRepository.findById(id)
            .map(categoria -> {
                // Atualiza os dados da categoria encontrada
                categoria.setNome(categoriaDetails.getNome());
                Categoria updatedCategoria = categoriaRepository.save(categoria);
                return ResponseEntity.ok(updatedCategoria);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /api/categorias/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        // Tenta encontrar a categoria a deletar
        if (!categoriaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        categoriaRepository.deleteById(id);
        // Retorna código 204 (No Content) após a exclusão bem-sucedida
        return ResponseEntity.noContent().build();
    }
}