package MyLib;

import java.util.ArrayList;
import java.util.List;

public class Agency {
    private static Agency instance;

    private String companyName;
    private List<Property> properties;
    private List<Agent> agents;
    private List<Client> clients;
    private List<Transaction> transactions;

    private Agency(String companyName)
    {
        this.companyName = companyName;
        this.properties = new ArrayList<>();
        this.agents = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.transactions = new ArrayList<>();
        seedData();
    }

    public static Agency getInstance()
    {
        if (instance == null)
        {
            instance = new Agency("Terralith Estate");
        }
        return instance;
    }

    private void seedData()
    {
        properties.add(new Property("Terralith Estate"));
        agents.add(new Agent("Rendouz Serrano", "09171234567", 0.05));
        agents.add(new Agent("McJaylord Goleña", "09181234567", 0.04));
        agents.add(new Agent("Chen Brigino", "09191234567", 0.045));
        agents.add(new Agent("Gabriel Sioson", "09301623877", 0.05));
        agents.add(new Agent("Steven Casio", "09214536767", 0.045));
    }

    public Report generateReport()
    {
        Report r = new Report(properties.get(0));
        r.compile();
        return r;
    }

    public void addTransaction(Transaction t)
    {
        transactions.add(t);
    }

    public void addClient(Client c)
    {
        clients.add(c);
    }

    public void addAgent(Agent a)
    {
        agents.add(a);
    }

    public Property getMainProperty()
    {
        return properties.get(0);
    }

    public List<Agent> getAgents()
    {
        return agents;
    }

    public List<Client> getClients()
    {
        return clients;
    }

    public List<Transaction> getTransactions()
    {
        return transactions;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public Client findClientByName(String name)
    {
        for (Client c : clients)
        {
            if (c.getName().equalsIgnoreCase(name))
            {
                return c;
            }
        }
        return null;
    }
}
