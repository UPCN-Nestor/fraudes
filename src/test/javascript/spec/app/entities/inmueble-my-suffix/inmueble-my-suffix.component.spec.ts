/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FrTestModule } from '../../../test.module';
import { InmuebleMySuffixComponent } from '../../../../../../main/webapp/app/entities/inmueble-my-suffix/inmueble-my-suffix.component';
import { InmuebleMySuffixService } from '../../../../../../main/webapp/app/entities/inmueble-my-suffix/inmueble-my-suffix.service';
import { InmuebleMySuffix } from '../../../../../../main/webapp/app/entities/inmueble-my-suffix/inmueble-my-suffix.model';

describe('Component Tests', () => {

    describe('InmuebleMySuffix Management Component', () => {
        let comp: InmuebleMySuffixComponent;
        let fixture: ComponentFixture<InmuebleMySuffixComponent>;
        let service: InmuebleMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [InmuebleMySuffixComponent],
                providers: [
                    InmuebleMySuffixService
                ]
            })
            .overrideTemplate(InmuebleMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InmuebleMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InmuebleMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new InmuebleMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.inmuebles[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
