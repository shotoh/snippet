export default function Products() {
  return (
    <div>
      <section id="products" className="p-4 space-y-4">
        <h1 className="text-4xl font-bold">Products</h1>
        <div className="m-2" id="products">
          <h2 className="text-2xl text-blue-800 font-bold">
            <a href="/login">Snippet</a>
          </h2>
          <div class="info" className="px-4 space-y-4">
            <h1 className="text-xl font-bold">
              <a href="https://docs.google.com/document/d/1mWRijmO2jGVkX1hFk0kQDrzhuhjkwUPxVCqmXQEwyck/edit">
                Logs
              </a>
            </h1>
            <h1 className="text-xl font-bold">
              <a href="https://docs.google.com/document/d/1Ckw3IO8BV81P6RQegKrOKW7k4r4YYTVAcBPVpRVovLs/edit?usp=sharing">
                Documentation
              </a>
            </h1>
          </div>
        </div>
      </section>
    </div>
  );
}
