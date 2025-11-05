// ===== Simple data model (replace with your API later) =====
const demoRows = [
  {
    title: "Trending Now",
    items: [
      { id: 1, title: "Neon Skies", year: "2024", rating: "PG-13", img: "https://picsum.photos/seed/neon/800/450" },
      { id: 2, title: "Quantum Edge", year: "2025", rating: "PG", img: "https://picsum.photos/seed/quantum/800/450" },
      { id: 3, title: "Crimson Tide", year: "2023", rating: "R", img: "https://picsum.photos/seed/crimson/800/450" },
      { id: 4, title: "Silver Line", year: "2022", rating: "PG-13", img: "https://picsum.photos/seed/silver/800/450" },
      { id: 5, title: "Aurora Run", year: "2021", rating: "PG", img: "https://picsum.photos/seed/aurora/800/450" },
      { id: 6, title: "Night Driver", year: "2025", rating: "PG-13", img: "https://picsum.photos/seed/driver/800/450" }
    ]
  },
  {
    title: "Top Picks For You",
    items: [
      { id: 7, title: "Echo Harbor", year: "2024", rating: "TV-14", img: "https://picsum.photos/seed/harbor/800/450" },
      { id: 8, title: "Cinder City", year: "2023", rating: "TV-MA", img: "https://picsum.photos/seed/cinder/800/450" },
      { id: 9, title: "Star Lane", year: "2022", rating: "PG", img: "https://picsum.photos/seed/starlane/800/450" },
      { id: 10, title: "Shadow Grid", year: "2025", rating: "PG-13", img: "https://picsum.photos/seed/shadow/800/450" }
    ]
  },
  {
    title: "New Releases",
    items: [
      { id: 11, title: "Orbit Nine", year: "2025", rating: "PG-13", img: "https://picsum.photos/seed/orbit/800/450" },
      { id: 12, title: "Glass River", year: "2025", rating: "PG", img: "https://picsum.photos/seed/glass/800/450" },
      { id: 13, title: "Last Beacon", year: "2025", rating: "R", img: "https://picsum.photos/seed/beacon/800/450" }
    ]
  }
];

// ===== Utilities =====
const $ = (sel, el = document) => el.querySelector(sel);
const el = (tag, cls) => {
  const e = document.createElement(tag);
  if (cls) e.className = cls;
  return e;
};

// ===== Hero builder =====
function renderHero(item) {
  const hero = $("#hero");
  hero.innerHTML = `
    <div class="hero__backdrop">
      <img src="${item.img}" alt="${item.title} backdrop" />
    </div>
    <div class="hero__content">
      <h1 class="hero__title">${item.title}</h1>
      <div class="hero__meta">${item.year} • ${item.rating}</div>
      <div class="hero__actions">
        <button class="btn btn--primary">▶ Play</button>
        <button class="btn btn--ghost">ℹ More Info</button>
      </div>
    </div>
  `;
}

// ===== Row (carousel) builder =====
function renderRow({ title, items }) {
  const row = el("section", "row");
  const h = el("h2", "row__title");
  h.textContent = title;
  const scroller = el("div", "scroller");

  items.forEach((m) => {
    const card = el("article", "card");
    card.innerHTML = `
      <div class="card__thumb">
        <img src="${m.img}" alt="${m.title}" loading="lazy"/>
      </div>
      <div class="card__meta">
        <div class="card__title">${m.title}</div>
        <div class="badges">
          <span class="badge">${m.year}</span>
          <span class="badge">${m.rating}</span>
        </div>
      </div>
    `;
    scroller.appendChild(card);
  });

  row.append(h, scroller);
  return row;
}

// ===== Search & Theme (bonus UX) =====
function setupSearch() {
  const input = $("#searchInput");
  if (!input) return;
  input.addEventListener("input", () => {
    const q = input.value.toLowerCase();
    document.querySelectorAll(".card").forEach((c) => {
      const t = c.querySelector(".card__title")?.textContent.toLowerCase() || "";
      c.style.display = t.includes(q) ? "" : "none";
    });
  });
}

function setupThemeToggle() {
  const btn = $("#themeToggle");
  if (!btn) return;
  let light = false;
  btn.addEventListener("click", () => {
    light = !light;
    document.documentElement.style.setProperty("--bg", light ? "#f6f7fb" : "#0b0b0f");
    document.documentElement.style.setProperty("--bg-elev", light ? "#ffffff" : "#12121a");
    document.documentElement.style.setProperty("--text", light ? "#0e0e12" : "#e9e9ef");
    document.documentElement.style.setProperty("--muted", light ? "#4b4b63" : "#a0a0b8");
    document.body.style.background = light
      ? "linear-gradient(0deg, #f6f7fb, #ffffff)"
      : "radial-gradient(1200px 600px at 20% -10%, #221a2d 0%, #0b0b0f 40%) no-repeat, #0b0b0f";
  });
}

// ===== Boot =====
function boot() {
  // Pick a random hero from first row
  const heroPick = demoRows[0].items[Math.floor(Math.random() * demoRows[0].items.length)];
  renderHero(heroPick);

  const rowsMount = $("#rows");
  rowsMount.innerHTML = "";
  demoRows.forEach((row) => rowsMount.appendChild(renderRow(row)));

  setupSearch();
  setupThemeToggle();
}

document.addEventListener("DOMContentLoaded", boot);
