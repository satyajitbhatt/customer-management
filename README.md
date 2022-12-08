[1] Each customer may have a maximum of 5 registered addresses - Made one list for store multiple address. - Check there is more than 5 address or not ? - If there is more than 5 address then it will give error "Address should not more then five"

[2] Each customer may have only 1 main address at the same time

Made one list for store address.
Check if address is valid or not ? If valid then check for only one address to make a primary
If we try to add more than one address as primary address at same time then it will give an error "Only One address should be primary address"
[3] Each customer must always have the main address

Made one method for check address is valid or not ?
Add all the address into list for check other address.
It will check there is at least one primary address or not ?
[4] CRUD for customer registration and address

CRUD For customer and address created
[5] Option to make the address as a primary

Made one condition for check address as primary address.
It will be boolean condition based on primary option selection
[6] Validation/Masks of Client fields (E-mail, CPF/CNPJ, type (PJ or PF), address, phone)

Email validation created with mandatory to add (@ and .)
CPF/CNPJ number validation created to add that number into specific format
Phone number validation created need to mandatory 10 digit
[7] Validation of address fields (street, number, neighborhood, city, zip code, state)

[8] RESTful API

Created RESTFul API for customer and address
[9] Spring Boot

Used spring boot for develop customer and address module
[10] App 100% tested (integrated, single)

Yes
[11] SOLID concepts

[12] GET with pagination and filters - Did the pagination using pageable

[13] Versioned on Github

[14] PostgreSQL, Embedded ou H2 - use PostgreSQL for the database

[15] Optimistic lock on insertion of new records (customer and address) -

[16] Contract change (endpoint) - version - Done in getAll()

[17] New customer needs different information than what is returned but needs to keep the old contract

[18] Applying Cache on a TEST endpoint - @EnableCaching

[19] Consume public endpoint for zip code query (https://viacep.com.br/) - Done using RestTemplate
