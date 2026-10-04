package com.kayo.libraryapi.repository;

import com.kayo.libraryapi.Services.TransacoesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransacoesTest {

    /**
     * Commit -> confirmar as alterações
     * Rollback -> desfazer as alterações
     */
    @Autowired
    TransacoesService transacoesService;

    @Test
    void TransacaoEstadoManaged(){
        transacoesService.atualizarSemAtualizar();
    }
    @Test
    public void executar(){

        transacoesService.executar();
        //O certo é dar erro por conta do if no rollback dentro de TransacoesService
    }
}
