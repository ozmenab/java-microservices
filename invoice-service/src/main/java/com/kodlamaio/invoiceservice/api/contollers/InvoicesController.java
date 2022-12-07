package com.kodlamaio.invoiceservice.api.contollers;

import com.kodlamaio.invoiceservice.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.business.responses.GetAllInvoicesResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invoices")
@AllArgsConstructor
public class InvoicesController {
    private InvoiceService invoiceService;

    @GetMapping
    public List<GetAllInvoicesResponse> getAll(){
        return invoiceService.getAll();
    }
}
