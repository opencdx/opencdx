package cdx.opencdx.iam.model;

import cdx.opencdx.grpc.provider.Address;
import cdx.opencdx.grpc.provider.AddressPurpose;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings({"java:S116","java:S125"})
public class AddressResponse {
    String country_code;
    String country_name;
    AddressPurpose address_purpose;
    String address_type;
    String address_1;
    String city;
    String state;
    String postal_code;
    String telephone_number;
    String fax_number;
    List<Address> addressList;

//    /**
//     * Constructor from a protobuf address
//     * @param addressList protobuf address
//     */
//    public AddressResponse(List<Address> addressList) {
////        addressList.stream().collect
//        for (Address address : addressList) {
//            this.country_code = address.getCountryCode();
//            this.country_name = address.getCountryName();
//            this.address_purpose = address.getAddressPurpose();
//            this.address_type = address.getAddressType();
//            this.address_1 = address.getAddress1();
//            this.city = address.getCity();
//            this.state = address.getState();
//            this.postal_code = address.getPostalCode();
//            this.telephone_number = address.getTelephoneNumber();
//            this.fax_number = address.getFaxNumber();
//            addressList.add(address);
//        }
}
