package com.controleestoque.api_estoque.controller;

import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.controleestoque.api_estoque.model.Produto;
import com.controleestoque.api_estoque.repository.ProdutoRepository;
import com.controleestoque.api_estoque.repository.CategoriaRepository;
import com.controleestoque.api_estoque.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;

    // Estoque é geralmente manipulado via Produto ou separadamente.

    // GET /api/produtos
    @GetMapping
    public List<Produto> getAllProdutos() {
        // Retorna a lista de Produtos. Pode ser necessário configurar DTOs para evitar loops infinitos com JSON.
        return produtoRepository.findAll();
    }

    // GET /api/produtos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getCategoriaById(@PathVariable Long id) {
        // Busca a categoria pelo ID. Usa Optional.orElse para retornar 404 se não encontrar.
        return produtoRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}