# Agenda de Eventos Locais - Botucatu
Protótipo funcional de uma agenda centralizadora de eventos e oportunidades para o município de Botucatu, desenvolvido em Java com suporte de backend do Firebase.

## Stack
- Linguagem: Java (Android SDK).
- Banco de Dados: Firebase Realtime Database.
- Autenticação: Firebase Auth.
- Integração: Google Maps (Através de Intents).

## Funcionalidades
- Sistema de Login e Cadastro: fluxo completo de autenticação de usuários.
- Feed de eventos: Interface estilo rede social com descrição, local, horários e espaço para fotos.

## Estrutura de Telas
- MainActivity (Tela de Splash)
<img width="216" height="480" alt="Screenshot_20260130_144634" src="https://github.com/user-attachments/assets/30b26ad2-7407-40b5-b060-0996a233a06d" />

- LoginActivity e CadastroActivity (Tela de Login e Tela de Cadastro de usuários )
<img width="216" height="480" alt="Screenshot_20260130_144005" src="https://github.com/user-attachments/assets/5d7d4a48-a2f3-4344-a0da-920046d30ae9" />
<img width="216" height="480" alt="Screenshot_20260130_144024" src="https://github.com/user-attachments/assets/45260802-10e9-4e80-a446-e8ebb3d100cd" />

- NavigationActivity (Tela de Navegação, contendo uma view para exibição de fragments: FeedFragment, CalendarioFragment, FavoritosFragment, PerfilFragment)

- FeedFragment (Exibição dos eventos e suas informações em formato de post)
<img width="216" height="480" alt="Screenshot_20260130_144122" src="https://github.com/user-attachments/assets/83d12ac3-9155-4731-b032-368c2d8d998c" />

- PerfilFragment (Exibição das informações do usuário, como nome, email e privilégio (moderação), podendo sair da conta e criar eventos por essa tela)
<img width="216" height="480" alt="Screenshot_20260130_144146" src="https://github.com/user-attachments/assets/844efdd7-ce64-4564-b93c-2c3bd99583fa" />

- DetalhesEventoActivity (Tela criada ao clicar em "Ver mais detalhes" de qualquer post, a tela exibe detalhes extras sobre o evento, inclusive a possibilidade de compartilha-lo e abrir seu endereço no Google Maps
<img width="216" height="480" alt="Screenshot_20260130_144132" src="https://github.com/user-attachments/assets/bd9e8632-4d48-438a-af96-7a9ec1ab282d" />

- CadEventoActivity (Tela de criação de eventos com as informações de título, descrição, local, data e hora, categoria e imagem. O evento é salvo no Firebase)
<img width="216" height="480" alt="Screenshot_20260130_144213" src="https://github.com/user-attachments/assets/11189c91-9688-435f-b851-46799a753353" />
