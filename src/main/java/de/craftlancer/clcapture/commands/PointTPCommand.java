package de.craftlancer.clcapture.commands;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.clcapture.CLCapture;
import de.craftlancer.clcapture.CapturePoint;

public class PointTPCommand extends CaptureSubCommand {
    
    public PointTPCommand(CLCapture plugin) {
        super(CLCapture.ADMIN_PERMISSION, plugin, true);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args) {
        if(!checkSender(sender))
            return "You're not allowed to use this command.";
        
        if (!(sender instanceof Player))
            return "You must be a player to teleport yourself.";
        if (args.length < 3)
            return "You must specify a point id.";
        
        Optional<CapturePoint> point = getPlugin().getPoints().stream().filter(a -> a.getId().equals(args[2])).findFirst();
        
        if (!point.isPresent())
            return "This point does not exist.";
        
        if (((Player) sender).teleport(point.get().getSignLocation()))
            return null;
        else
            return "Something went wrong while teleporting.";
    }
    
    @Override
    protected List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 2)
            return getPlugin().getPoints().stream().map(CapturePoint::getId).collect(Collectors.toList());
        if (args.length == 3)
            return getPlugin().getPoints().stream().map(CapturePoint::getId).filter(a -> a.startsWith(args[2])).collect(Collectors.toList());
        
        return Collections.emptyList();
    }
    
    @Override
    public void help(CommandSender sender) {
        // TODO Auto-generated method stub
        
    }
    
}
