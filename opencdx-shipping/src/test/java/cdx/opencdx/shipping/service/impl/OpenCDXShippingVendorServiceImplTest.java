package cdx.opencdx.shipping.service.impl;

import cdx.opencdx.grpc.common.Address;
import cdx.opencdx.grpc.shipping.Order;
import cdx.opencdx.grpc.shipping.Shipping;
import cdx.opencdx.grpc.shipping.ShippingRequest;
import cdx.opencdx.grpc.shipping.ShippingVendorResponse;
import cdx.opencdx.shipping.model.OpenCDXShippingModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenCDXShippingVendorServiceImplTest {

    OpenCDXShippingVendorServiceImpl openCDXShippingVendorServiceImpl;

    @BeforeEach
    void beforeEach() {
        openCDXShippingVendorServiceImpl = new OpenCDXShippingVendorServiceImpl();
    }

    @RepeatedTest(100)
    void getShippingVendors() {
        ShippingRequest shippingRequest = ShippingRequest.newBuilder()
                .setDeclaredValue(Math.random() * 100)
                .setRequireSignature(Math.random() > 0.5)
                .setPackageDetails(Order.getDefaultInstance())
                .setSenderAddress(Address.getDefaultInstance())
                .setRecipientAddress(Address.getDefaultInstance())
                .build();
        Assertions.assertDoesNotThrow(() -> openCDXShippingVendorServiceImpl.getShippingVendors(shippingRequest));
    }

    @Test
    void fedexShippingVendor() {
        Shipping shipping = Shipping.newBuilder()
                .setShippingVendorId("fedex")
                .setDeclaredValue(Math.random() * 100)
                .setRequireSignature(Math.random() > 0.5)
                .setPackageDetails(Order.getDefaultInstance())
                .setSenderAddress(Address.getDefaultInstance())
                .setRecipientAddress(Address.getDefaultInstance())
                .build();

        Assertions.assertDoesNotThrow(() -> openCDXShippingVendorServiceImpl.shipPackage(shipping));
    }

    @Test
    void upsShippingVendor() {
        Shipping shipping = Shipping.newBuilder()
                .setShippingVendorId("ups")
                .setDeclaredValue(Math.random() * 100)
                .setRequireSignature(Math.random() > 0.5)
                .setPackageDetails(Order.getDefaultInstance())
                .setSenderAddress(Address.getDefaultInstance())
                .setRecipientAddress(Address.getDefaultInstance())
                .build();

        Assertions.assertDoesNotThrow(() -> openCDXShippingVendorServiceImpl.shipPackage(shipping));
    }
    @Test
    void uspsShippingVendor() {
        Shipping shipping = Shipping.newBuilder()
                .setShippingVendorId("usps")
                .setDeclaredValue(Math.random() * 100)
                .setRequireSignature(Math.random() > 0.5)
                .setPackageDetails(Order.getDefaultInstance())
                .setSenderAddress(Address.getDefaultInstance())
                .setRecipientAddress(Address.getDefaultInstance())
                .build();

        Assertions.assertDoesNotThrow(() -> openCDXShippingVendorServiceImpl.shipPackage(shipping));
    }
    @Test
    void doorDashShippingVendor() {
        Shipping shipping = Shipping.newBuilder()
                .setShippingVendorId("doordash")
                .setDeclaredValue(Math.random() * 100)
                .setRequireSignature(Math.random() > 0.5)
                .setPackageDetails(Order.getDefaultInstance())
                .setSenderAddress(Address.getDefaultInstance())
                .setRecipientAddress(Address.getDefaultInstance())
                .build();

        Assertions.assertDoesNotThrow(() -> openCDXShippingVendorServiceImpl.shipPackage(shipping));
    }
}