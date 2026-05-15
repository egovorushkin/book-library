# Requirements: SimpleLibrary

## Functional Requirements

| ID     | Title               | User Story                                                                                                                          | Priority | Status |
|--------|---------------------|-------------------------------------------------------------------------------------------------------------------------------------|----------|--------|
| FR-001 | Sign In             | As a member, I want to sign in with my credentials so that I can access the library system.                                         | High     | Open   |
| FR-002 | Sign Out            | As a member, I want to sign out so that my session ends securely.                                                                   | High     | Open   |
| FR-003 | Search Catalog      | As a member, I want to search the book catalog by title or author so that I can find books quickly.                                 | High     | Open   |
| FR-004 | View Availability   | As a member, I want to see whether a book is currently available so that I know if I can borrow it.                                 | High     | Open   |
| FR-005 | Borrow Book         | As a member, I want to borrow an available book so that I can take it home.                                                         | High     | Open   |
| FR-006 | Return Book         | As a member, I want to return a borrowed book so that it becomes available to other members.                                        | High     | Open   |
| FR-007 | View My Loans       | As a member, I want to see a list of my active loans so that I know which books I currently have.                                   | High     | Open   |
| FR-008 | View Active Loans   | As a librarian, I want to see all active loans in real time so that I know who has which book at any moment.                        | High     | Open   |
| FR-009 | Manage Catalog      | As a librarian, I want to add, edit, and remove books from the catalog so that the catalog stays accurate.                          | High     | Open   |
| FR-010 | Manage Members      | As a librarian, I want to create and manage member accounts so that patrons can access the system.                                  | High     | Open   |
| FR-011 | View Loan History   | As a librarian, I want to view the complete loan history so that I can audit who borrowed what and when.                            | Medium   | Open   |

## Non-Functional Requirements

| ID      | Title              | Requirement                                                                                              | Category    | Priority | Status |
|---------|--------------------|----------------------------------------------------------------------------------------------------------|-------------|----------|--------|
| NFR-001 | Page Load Time     | All page loads must complete within 3 seconds on a standard broadband connection.                        | Performance | High     | Open   |
| NFR-002 | Role Enforcement   | Librarian-only views must return HTTP 403 to unauthorized users in 100% of attempts.                     | Security    | High     | Open   |
| NFR-003 | Password Storage   | All passwords must be stored using bcrypt with a cost factor of at least 10.                             | Security    | High     | Open   |
| NFR-004 | Session Expiry     | Authenticated sessions must expire after 30 minutes of inactivity.                                      | Security    | Medium   | Open   |
| NFR-005 | Concurrent Users   | System must support at least 20 concurrent users without page load times exceeding 3 seconds.            | Scalability | Low      | Open   |

## Constraints

| ID    | Title                  | Constraint                                                                                     | Category  | Priority | Status |
|-------|------------------------|------------------------------------------------------------------------------------------------|-----------|----------|--------|
| C-001 | Java Version           | Backend must run on Java 25 LTS.                                                               | Technical | High     | Open   |
| C-002 | UI Framework           | UI must be built with Vaadin 25.                                                               | Technical | High     | Open   |
| C-003 | Database               | System must use PostgreSQL as the database.                                                    | Technical | High     | Open   |
| C-004 | Backend Framework      | Application must use Spring Boot with Spring Security.                                         | Technical | High     | Open   |
| C-005 | Data Access            | All database queries must use jOOQ 3.x; no JPA/Hibernate.                                     | Technical | High     | Open   |
| C-006 | Schema Migrations      | All schema changes must be applied via Flyway versioned migration scripts.                     | Technical | High     | Open   |
| C-007 | Browser Support        | UI must work correctly in Chrome, Firefox, and Safari (latest 2 major versions each).          | Technical | Medium   | Open   |
| C-008 | No Payments or Fines   | System must not implement payment processing or fine calculation.                              | Business  | High     | Open   |
| C-009 | No Reservations        | System must not implement book reservations or waiting lists.                                  | Business  | High     | Open   |
| C-010 | No Multi-Branch        | System must support a single library location only.                                            | Business  | High     | Open   |
| C-011 | No Self-Service Signup | Members cannot register themselves; the librarian creates all member accounts.                 | Business  | High     | Open   |