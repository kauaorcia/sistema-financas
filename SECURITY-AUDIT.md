# Independent Security Audit

## Scope

This document records an independent review performed against the public upstream repository `kauaorcia/sistema-financas` and the fork used for this contribution.

## Important limitation

The contributor and the BDK engineering process prepared and reviewed these changes together, but the application has **not been built or executed locally on the contributor's computer as part of this contribution**. The maintainer should run the full Maven test/build and application-level verification in their own environment before merging or deploying.

## Finding: authenticated users are not consistently bound to resource ownership

The API protects most routes with authentication, but resource endpoints accept identifiers supplied by the caller and the service layer performs direct repository access without checking that the authenticated principal owns the referenced resource.

Examples observed in the upstream code include:

- `GET /contas/usuario/{usuarioId}`
- `GET /contas/{id}`
- `DELETE /contas/{id}`
- `GET /transacoes/conta/{contaId}`
- `GET /transacoes/{id}`
- `DELETE /transacoes/{id}`
- `GET /transacoes/usuario/{usuarioId}/relatorio/saldo`

The security configuration requires authentication for these routes, but authentication alone does not establish authorization for the specific user's financial records.

### Risk

This creates a potential IDOR/BOLA class of authorization vulnerability: an authenticated user may be able to access or modify another user's financial resources if they can discover or guess resource identifiers.

### Evidence

The upstream `SecurityConfig` ends with `.anyRequest().authenticated()`, while `ContaService` and `TransacaoService` accept caller-provided IDs and query/delete directly without an ownership predicate.

### Contribution approach

This fork adds a narrow authorization guard at the service boundary using the authenticated `Usuario` principal. The objective is to make ownership explicit before reading, creating, or deleting account/transaction resources rather than relying on the identifier alone.

## Verification status

- Static review: performed against upstream source.
- Automated tests in this fork: added for the authorization boundary.
- Local build/run on contributor machine: **NOT PERFORMED**.
- Maintainer verification required before merge: **YES**.

## Recommendation

Run `./mvnw test` and, where possible, an integration test suite with two users demonstrating that user A cannot read, create against, report on, or delete user B's accounts/transactions.
