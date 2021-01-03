package de.basicbit.system.minecraft.listeners.world;

import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;

import de.basicbit.system.minecraft.Listener;

public class DisableWeather extends Listener {
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		if (e.toWeatherState()) {
			e.setCancelled(true);
		}
	}
	
}
