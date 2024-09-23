

<p align="center">
  <img src="https://github.com/PetJournal/petjournal.android/assets/42920754/e5af3405-67a0-471a-9c4e-c76877d8dda5" width="16.0%">
</p>



# PetJournal

## Dretrizes:
Ao entrar em nosso projeto voluntário, valorizamos sua contribuição e entusiasmo. No entanto, é importante estar ciente de algumas diretrizes essenciais para manter a integridade e o propósito do projeto. Primeiramente, familiarize-se com a documentação existente, que detalha o estado atual e os objetivos do projeto. Isso é crucial para entender onde estamos e para onde estamos indo. Em segundo lugar, pedimos que evite refatorações desnecessárias ou alterações radicais. Tais mudanças podem levar à estagnação e desviar-nos do nosso caminho. Busque sempre aprimorar e construir sobre o que já foi alcançado, em vez de reconstruir do zero. Ao seguir essas diretrizes, você ajudará a preservar a essência do projeto e a garantir que nosso trabalho coletivo continue evoluindo de forma produtiva e reconhecível.

## 1. Introdução ao Projeto

- **Objetivo**: O "Pet Journal" é uma ferramenta de gerenciamento para a vida dos pets, ajudando no controle de vacinas, vermífugos, rações, e outros aspectos importantes do cuidado animal.

- **Público-alvo**: Direcionado a donos de pets que desejam manter um controle organizado e acessível sobre a saúde e bem-estar de seus animais.

## 2. Configuração do Ambiente de Desenvolvimento

- **Pré-requisitos**:
    - **Android Studio** (versão mais recente) - (Giraffe | 2022.3.1)
    - **Kotlin SDK** (min: 24, max: 34)
    - **Ndk Version**: 23.1.7779620
    - **Gradle:** 8.1.3
- **Configuração Local**:
    1. Clone o repositório do projeto.
    2. Abra o projeto no Android Studio.
    3. Permita que o Android Studio sincronize o projeto com os arquivos Gradle.
    4. Configure um emulador ou conecte um dispositivo que suporte Android SDK versão 24 até 34.
    5. Execute o projeto para verificar se tudo está funcionando.

## 3. Arquitetura do Aplicativo
- [Architecture](doc/architecture/architecture.md)


## 4. Telas e seus diagramas de funcionamento
Nesta seção, você encontrará informações detalhadas sobre as diferentes telas do nosso aplicativo e sobre seus funcionamentos. 
- [Splash](doc/screens/splash_screen.md)
- [Login](doc/screens/login.md)
- [Cadastro](doc/screens/cadastro.md)
- [Esqueceu a senha](doc/screens/esqueceu_a_senha.md)
- [Aguardando código](doc/screens/aguardando_codigo.md)
- [Troque a senha](doc/screens/troque_a_senha.md)
- [Ambiente do usuário](doc/screens/ambiente_do_usuario.md)
- [Tela padrão inicial](doc/screens/tela_padrao_inicial.md)
- [Tela padrão espécie](doc/screens/tela_padrao_especie.md)
- [Tela padrão nome e gênero](doc/screens/tela_padrao_nome_genero.md)
- [Tela padrão raça e porte](doc/screens/tela_padrao_raca_e_porte.md)
- [Tela padrão data de Nascimento](doc/screens/tela_padrao_data_de_nascimento.md)


## 5. Padrões de Design e Melhores Práticas
 Utilizamos os recursos a baixo em nosso app, o que torna importante conhecer tais padrões.   
- [FormEvent](doc/pattern/FormEvent.md)
- [FormState](doc/pattern/FormState.md)
- [FormState e FormEvent](doc/pattern/FormStateAndFormEvent.md)

## 6. Integração com Backend

- A API que estamos usando é do próprio projeto, criada pela equipe de backend, para saber mais a respeito, por favor, visite o repositório do projeto: [Backend](https://github.com/PetJournal/petjournal.api)


## 7. Testes

- **Testes Unitários:**
    - **Objetivo:** Testar partes individuais do código (funções, métodos) de forma isolada.
    - **Como Fazemos:** Utilizamos JUnit, Mockk para testes unitários. Mock objects são usados para simular dependências externas.
    - **Benefícios:** Ajuda a encontrar bugs cedo, facilita a manutenção e melhora a qualidade do código.
- **Testes Instrumentados:**
    - **Objetivo:** Testar componentes do aplicativo no ambiente de um dispositivo Android real ou emulador.
    - **Como Fazemos:** Utilizamos Espresso para simular interações do usuário com a interface gráfica.
    - **Benefícios:** Garante que a interface do usuário funcione bem e identifica problemas de integração e performance.
- **Boas Práticas:**
    - Automatizamos os testes para integração contínua.
    - Buscamos alta cobertura de código, mas também focamos na qualidade dos testes.
    - Documentamos os testes claramente para facilitar a compreensão e manutenção.
- **Conclusão:**
Nossa abordagem de teste ajuda a manter a qualidade e estabilidade do software, facilitando a contribuição de novos integrantes e a manutenção do projeto.

## 8. Gerenciamento de Dependências

- **Bibliotecas**:
    1. **Coil:** Biblioteca de carregamento de imagens para Kotlin, otimizada para desempenho e eficiência.
    2. **Koin:** Framework de injeção de dependência leve para Kotlin.
    3. **Room:** Biblioteca de abstração de banco de dados do Android, que facilita o acesso ao SQLite.
    4. **Retrofit:** Cliente HTTP tipo-safe para Android e Java.
    5. **Navigation:** Componente do Android Jetpack para facilitar a navegação entre telas.
    6. **Material Design You:** Versão mais recente do Material Design, com foco em personalização e adaptabilidade.
    7. **Compose:** Toolkit moderno de UI do Android para criar interfaces nativas com menos código.
    8. **Coroutines:** Recurso do Kotlin para programação assíncrona e não bloqueante.

## 9. Contribuindo

- **Como Contribuir**:
    1. **Explore o Repositório:** Acesse o repositório no GitHub, leia atentamente o 'README', para compreender as diretrizes do projeto.
    2. **Fork e Clone:** Faça um fork do projeto para sua conta no GitHub e clone-o para seu ambiente de desenvolvimento local.
    3. **Identifique Oportunidades de Contribuição:** Examine as issues abertas para encontrar problemas que você pode resolver ou áreas onde pode melhorar a documentação ou o código.
    4. **Faça suas Alterações:** Trabalhe nas mudanças localmente, mantendo-se fiel às práticas e padrões de codificação do projeto.
    5. **Envie um Pull Request:** Após finalizar suas alterações, envie um pull request para o repositório original com uma descrição clara do que foi modificado.
    6. **Aguarde Feedback:** Esteja aberto para receber feedback dos mantenedores do projeto e pronto para fazer ajustes em seu pull request conforme necessário.
- **Padrões de Código**: Convenções de nomenclatura, formatação, uso de linters.
    1. **Comentários e Documentação:** Escreva comentários claros no código para explicar a lógica complexa e mantenha a documentação atualizada para facilitar a compreensão e colaboração.
    2. **Princípios de Design de Código:** Siga princípios como SOLID para estruturar o código de forma coesa, DRY para evitar repetições, e KISS para manter a simplicidade.
    3. **Padrões de Arquitetura:** Utilize padrões como MVVM para organizar o código de forma lógica, facilitando manutenção e expansão.
    4. **Revisão de Código:** Implemente revisões de código regulares para garantir a qualidade, aderência aos padrões e promover o aprendizado entre os desenvolvedores.
    5. **Testes Unitários e Integrados:** Encoraje a escrita de testes para validar individualmente cada componente e suas integrações, assegurando a robustez do software.
    6. **Gerenciamento de Dependências:** Mantenha as dependências bem gerenciadas e atualizadas para evitar conflitos e vulnerabilidades.
    7. **Tratamento de Erros:** Adote uma abordagem consistente para o tratamento de erros, garantindo que o sistema se comporte de maneira previsível e segura.
    8. **Segurança:** Siga as melhores práticas de segurança no código para prevenir vulnerabilidades, como injeção de SQL e vazamento de dados, sempre procure compreender a ação que esta realizando para evitar problemas neste quesito.
    9. **Performance:** Otimize o código para melhorar a performance, considerando aspectos como tempo de execução e consumo de recursos, para isso, não há problema em utilizar ferramentas de inteligência artificial para otimizar o código e o seu tempo, desde que compreenda o que foi feito e saiba explicar.

## 10. FAQs e Solução de Problemas

- **Perguntas, dúvidas ou problemas**: Sinta-se a vontade para abrir uma essue [Clicando aqui](https://github.com/PetJournal/petjournal.android/issues)

## Contribuições


[![NotyKT Contributors](https://contrib.rocks/image?repo=PetJournal/petjournal.android)](https://github.com/PetJournal/petjournal.android/graphs/contributors)


## Licença

Este projeto é licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.
