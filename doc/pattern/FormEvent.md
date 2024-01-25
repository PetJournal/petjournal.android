### O que é FormEvent?

`FormEvent` é uma classe selada (sealed class) em Kotlin, usada na tela de cadastro de usuário do nosso aplicativo. Esta classe representa diferentes tipos de eventos que podem ocorrer durante a interação do usuário com o formulário de cadastro.

### O que isso faz no código?

Cada subclasse de `FormEvent` corresponde a um evento específico no formulário, como mudança de nome, sobrenome, e-mail, etc. Quando um usuário interage com o formulário (por exemplo, digitando um nome), um evento correspondente (neste caso, `NameChanged`) é emitido. Este evento é então tratado pelo ViewModel, que atualiza o estado do formulário de acordo com a ação do usuário, garantindo que a interface do usuário reflita essas mudanças de forma eficiente e evitando recomposições desnecessárias.

### Como devo utilizar em outras telas?

Para utilizar um padrão semelhante em outras telas, siga estas etapas:

1. **Defina uma classe selada para eventos**: Crie uma classe selada para representar os diferentes tipos de eventos que podem ocorrer na tela.
  ```kotlin
  sealed class ScreenEvent {
      object EventA : ScreenEvent()
      data class EventB(val data: String) : ScreenEvent()
      data class EventC(val value: Int) : ScreenEvent()
  }
  ```
2. **Manipule os eventos no ViewModel**: No ViewModel da tela, implemente um método para lidar com os eventos, atualizando o estado da tela conforme necessário.
  ```kotlin
    data class ScreenState(
      val isLoading: Boolean = false,
      val data: String = "",
      val error: String? = null
  )
  ```
  
  ```kotlin
  class ScreenViewModel : ViewModel() {
      private val _state = MutableLiveData<ScreenState>()
      val state: LiveData<ScreenState> = _state
  
      fun onEvent(event: ScreenEvent) {
          when (event) {
              is ScreenEvent.EventA -> {
                  // Lógica para o EventA
              }
              is ScreenEvent.EventB -> {
                  // Lógica para o EventB, utilizando event.data
              }
              is ScreenEvent.EventC -> {
                  // Lógica para o EventC, utilizando event.value
              }
          }
      }
  }
  
  ```
3. **Emita eventos a partir da UI**: Na interface do usuário, emita os eventos apropriados em resposta às ações do usuário.
  ```kotlin
  @Composable
  fun ScreenUI(viewModel: ScreenViewModel) {
      Button(onClick = { viewModel.onEvent(ScreenEvent.EventA) }) {
          Text("Acionar EventA")
      }
      TextField(
          value = "",
          onValueChange = { viewModel.onEvent(ScreenEvent.EventB(it)) }
      )
      Slider(
          value = 0f,
          onValueChange = { viewModel.onEvent(ScreenEvent.EventC(it.toInt())) }
      )
  }
  ```
### Como sei quando devo usar?

Você deve usar `FormEvent` ou um padrão similar sempre que precisar gerenciar o estado de uma tela de forma eficiente, especialmente em formulários ou em interfaces onde as ações do usuário resultam em mudanças de estado. Esse padrão é particularmente útil em aplicações que utilizam Jetpack Compose, onde a eficiência na recomposição da UI é crucial.

### Conclusão

Usar `FormEvent` ajuda a manter uma arquitetura clara e reativa, facilitando o gerenciamento de estado e melhorando a performance da interface do usuário.

---

Nota: Sinta-se à vontade para adaptar ou expandir este texto conforme necessário para nossa documentação!