package com.kodlamaio.invoiceservice.business.concretes;

import com.kodlamaio.common.util.mapping.ModelMapperService;
import com.kodlamaio.invoiceservice.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.business.responses.GetAllInvoicesResponse;
import com.kodlamaio.invoiceservice.entities.Invoice;
import com.kodlamaio.invoiceservice.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InvoiceManager implements InvoiceService {
    private InvoiceRepository invoiceRepository;
    private ModelMapperService modelMapperService;

    @Override
    public void add(Invoice invoice) {
        invoice.setId(UUID.randomUUID().toString());
        invoiceRepository.save(invoice);
    }

    @Override
    public List<GetAllInvoicesResponse> getAll() {
        List<Invoice> invoiceList = invoiceRepository.findAll();
        List<GetAllInvoicesResponse> response = invoiceList.stream()
                .map(invoice -> modelMapperService.forResponse().map(invoice,GetAllInvoicesResponse.class))
                .collect(Collectors.toList());
        return response;
    }
}
