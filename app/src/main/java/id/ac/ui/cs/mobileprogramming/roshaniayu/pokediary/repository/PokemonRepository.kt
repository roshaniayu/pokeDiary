package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.repository

import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.PokemonDao
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.PokemonEntity
import kotlinx.coroutines.*

class PokemonRepository private constructor(
    private val pokemonDao: PokemonDao
) {
    private val allPokemon: LiveData<List<PokemonEntity>> = pokemonDao.getAll()

    @ObsoleteCoroutinesApi
    var thread = newSingleThreadContext("pokemonRepository") as CoroutineDispatcher

    @ObsoleteCoroutinesApi
    fun insertPokemon(pokemon: PokemonEntity) = GlobalScope.launch(thread) {
        pokemonDao.insert(pokemon)
    }

    fun getAllPokemon(): LiveData<List<PokemonEntity>> {
        return allPokemon
    }

    @ObsoleteCoroutinesApi
    fun updatePokemon(contacts: PokemonEntity) = GlobalScope.launch(thread) {
        pokemonDao.update(contacts)
    }

    @ObsoleteCoroutinesApi
    fun deletePokemon(contacts: PokemonEntity) = GlobalScope.launch(thread) {
        pokemonDao.delete(contacts)
    }

    companion object {
        @Volatile private var instance: PokemonRepository? = null

        fun getInstance(pokemonDao: PokemonDao) =
            instance ?: synchronized(this) {
                instance ?: PokemonRepository(pokemonDao).also { instance = it }
            }
    }
}