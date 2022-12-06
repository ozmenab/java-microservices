package com.kodlamaio.invoiceservice.business.concretes;

import com.kodlamaio.invoiceservice.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.entities.Invoice;
import com.kodlamaio.invoiceservice.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InvoiceManager implements InvoiceService {
    private InvoiceRepository invoiceRepository;

    @Override
    public void add(Invoice invoice) {
        invoiceRepository.save(invoice);
    }
}
