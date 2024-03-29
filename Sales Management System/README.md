# spring-app

<h2>Endpoints</h2>

<h3>User Authentication</h3>
<ul>
    <li><strong>POST</strong>: <code>/auth/register</code> - Register a new user.</li>
    <li><strong>POST</strong>: <code>/auth/login</code> - Login with existing credentials.</li>
</ul>

<h3>Product Management</h3>
<ul>
    <li><strong>GET</strong>: <code>/api/product-categories</code> - Retrieve all product categories.</li>
    <li><strong>GET, POST</strong>: <code>/api/products</code> - Retrieve all products or add a new product.</li>
</ul>

<h3>Client Management</h3>
<ul>
    <li><strong>GET, POST</strong>: <code>/api/clients</code> - Retrieve all clients or add a new client.</li>
</ul>

<h3>Sales Management</h3>
<ul>
    <li><strong>GET, POST</strong>: <code>/api/sales</code> - Retrieve all sales or add a new sale.</li>
</ul>

<h3>Reports</h3>
<ul>
    <li><strong>GET</strong>: <code>/api/reports/sales?startDate=YYYY-MM-DD&amp;endDate=YYYY-MM-DD</code> - Retrieve sales report within a date range.</li>
    <li><strong>GET</strong>: <code>/api/reports/clients</code> - Retrieve clients report.</li>
    <li><strong>GET</strong>: <code>/api/reports/products</code> - Retrieve products report.</li>
</ul>

